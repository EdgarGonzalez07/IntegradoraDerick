from flask import Flask, request, jsonify
from flask_cors import CORS
import sqlite3
import os
import datetime
import pytz

app = Flask(__name__)
CORS(app)

DataBase_NAME = "Integradora.db"

LOG_REGISTRATIONS = "h_registros.txt"
LOG_LOGINS = "h_logins.txt"
LOG_UPDATES = "h_updates.txt"
LOG_DELETES = "h_deletes.txt"

def get_cdmx_time():
    mx_time = datetime.datetime.now(pytz.timezone('America/Mexico_City'))
    return mx_time.strftime("%Y-%m-%d %H:%M:%S")

def init_db():
    print(">>> Ejecutando init_db()...")
    conn = sqlite3.connect(DataBase_NAME)
    c = conn.cursor()
    print(">>> Creando tabla si no existe...")
    c.execute('''CREATE TABLE IF NOT EXISTS usuarios
                 (id INTEGER PRIMARY KEY AUTOINCREMENT,
                  username TEXT NOT NULL,
                  email TEXT NOT NULL UNIQUE,
                  password TEXT NOT NULL)''')
    conn.commit()
    conn.close()
    print(">>> BD lista!")

def log_event(filename, message):
    with open(filename, "a", encoding="utf-8") as f:
        f.write(f"[{get_cdmx_time()}] {message}\n")

@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')

    if not username or not email or not password:
        return jsonify({'error': 'Missing Failed'}), 400

    try:
        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()
        c.execute("INSERT INTO usuarios (username, email, password) VALUES (?, ?, ?)",
                  (username, email, password))
        conn.commit()
        user_id = c.lastrowid
        conn.close()

        log_event(LOG_REGISTRATIONS, f"Usuario nuevo registrado: id={user_id}, nombre={username}, correo={email}")

        return jsonify({'id': user_id, 'username': username, 'email': email}), 201
    except sqlite3.IntegrityError:
        return jsonify({'error': 'El email que intentas registrar ya existe.'}), 409
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/usuarios', methods=['GET'])
def get_all_users():
    try:
        conn = sqlite3.connect(DataBase_NAME)
        conn.row_factory = sqlite3.Row  # To return dict-like objects
        c = conn.cursor()
        c.execute("SELECT id, username, email, password FROM usuarios")
        rows = c.fetchall()

        lista_usuarios = []
        for row in rows:
            lista_usuarios.append({
                "id": row["id"],
                "username": row["username"],
                "email": row["email"],
                "password": row["password"]
            })

        conn.close()
        return jsonify(lista_usuarios), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')

    if not email or not password:
        return jsonify({'error': 'archivos extraños/corruptos'}), 400

    conn = sqlite3.connect(DataBase_NAME)
    c = conn.cursor()
    c.execute("SELECT id, username, email FROM usuarios WHERE email = ? AND password = ?", (email, password))
    user = c.fetchone()
    conn.close()

    if user:
        # Log Login
        log_event(LOG_LOGINS, f"Usuario validado: {user[1]} ({user[2]})")
        return jsonify({'id': user[0], 'username': user[1], 'email': user[2]}), 200
    else:
        return jsonify({'error': 'credenciales invalidas'}), 401

@app.route('/usuarios/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    data = request.get_json()
    username = data.get('username')
    email = data.get('email')

    try:
        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()

        # Get old data for log
        c.execute("SELECT username, email FROM usuarios WHERE id = ?", (user_id,))
        old_user = c.fetchone()

        c.execute("UPDATE usuarios SET username = ?, email = ? WHERE id = ?", (username, email, user_id))
        conn.commit()
        conn.close()

        if old_user:
            log_event(LOG_UPDATES, f"Usuario que fue modificado: id={user_id}: pasa de [{old_user[0]}, {old_user[1]}] a [{username}, {email}]")

        return jsonify({'id': user_id, 'username': username, 'email': email}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/usuarios/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    try:
        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()

        # Get user for log
        c.execute("SELECT username, email FROM usuarios WHERE id = ?", (user_id,))
        usertrace = c.fetchone()

        c.execute("DELETE FROM usuarios WHERE id = ?", (user_id,))
        conn.commit()
        conn.close()

        if usertrace:
            log_event(LOG_DELETES, f"Usuario que fue eliminado: id={user_id}: {usertrace[0]} ({usertrace[1]})")

        return jsonify({'message': 'User deleted'}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == "__main__":
    init_db()   # <--- AQUÍ SE CREA LA BD Y LA TABLA
    app.run(host="0.0.0.0", port=5000, debug=True)  # <--- AQUI SE DA ACCESO A CUALQUIER IP QUE QUIERA ENTRAR