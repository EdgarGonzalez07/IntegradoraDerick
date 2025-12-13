from flask import Flask, request, jsonify, send_from_directory
from flask_cors import CORS
from werkzeug.utils import secure_filename
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

UPLOAD_FOLDER = "uploads"
app.config["UPLOAD_FOLDER"] = UPLOAD_FOLDER
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

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

    c.execute('''CREATE TABLE IF NOT EXISTS almacenes
                 (id INTEGER PRIMARY KEY AUTOINCREMENT,
                  name TEXT NOT NULL,
                  location TEXT NOT NULL UNIQUE,
                  capacity INTEGER,
                  image TEXT NOT NULL)''')
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
        conn.close()
        return jsonify({'error': 'El email que intentas registrar ya existe.'}), 409
    except Exception as e:
        conn.close()
        return jsonify({'error': str(e)}), 500

@app.route('/almacenes', methods=['POST'])
def register_almacen():
    data = request.get_json()
    print("DATA RECIBIDA:", data)
    try:
        name = data.get('name')
        location = data.get('location')
        capacity = int(data.get('capacity'))
        image = data.get('image')

        if not name or not location or capacity is None or not image:
            return jsonify({'error': 'Faltan campos requeridos'}), 400

        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()
        c.execute("""
            INSERT INTO almacenes (name, location, capacity, image)
            VALUES (?, ?, ?, ?)
        """,(name, location, capacity, image))
        conn.commit()
        almacen_id = c.lastrowid
        conn.close()

        return jsonify({
            'id': almacen_id,
            'name': name,
            'location': location,
            'capacity': capacity,
            'imgUrl': image
        }), 201
    except ValueError:
        conn.close()
        return jsonify({'error': 'Capacidad debe ser un número'}), 400
    except sqlite3.IntegrityError:
        conn.close()
        return jsonify({'error': 'La ubicación ya existe'}), 409
    except Exception as e:
        print("ERROR:", e)
        conn.close()
        return jsonify({'error': str(e)}), 500

@app.route('/upload', methods=['POST'])
def upload_image():
    if "image" not in request.files:
        return jsonify({"error": "No image file"}), 400

    image = request.files["image"]

    if image.filename == "":
        return jsonify({"error": "Empty filename"}), 401

    filename = secure_filename(image.filename)
    unique_name = f"{int(datetime.datetime.now().timestamp())}_{filename}"

    save_path = os.path.join(app.config["UPLOAD_FOLDER"], unique_name)
    image.save(save_path)

    image_url = f"https://{request.host}/uploads/{unique_name}"

    return jsonify({
        "url": image_url
    }), 201

@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config["UPLOAD_FOLDER"], filename)


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
        conn.close()
        return jsonify({'error': str(e)}), 500

@app.route('/almacenes', methods=['GET'])
def obtener_almacenes():
    try:
        conn = sqlite3.connect(DataBase_NAME)
        conn.row_factory = sqlite3.Row
        c = conn.cursor()
        c.execute("SELECT * FROM almacenes")
        rows = c.fetchall()

        almacenes = []
        for row in rows:
            almacenes.append({
                "id": row["id"],
                "name": row["name"],
                "location": row["location"],
                "capacity": row["capacity"],
                "image": row["image"]
            })
        conn.close()
        return jsonify(almacenes), 200

    except Exception as e:
        conn.close()
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

@app.route('/almacenes/<int:almacen_id>', methods=['GET'])
def obtener_almacen(almacen_id):
    conn = sqlite3.connect(DataBase_NAME)
    conn.row_factory = sqlite3.Row
    c = conn.cursor()
    c.execute("SELECT * FROM almacenes WHERE id = ?", (almacen_id,))
    row = c.fetchone()
    conn.close()

    if row:
        return jsonify({
            "id": row["id"],
            "name": row["name"],
            "location": row["location"],
            "capacity": row["capacity"],
            "image": row["image"]
        }), 200
    else:
        return jsonify({'error': 'Almacén no encontrado'}), 404

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
        conn.close()
        return jsonify({'error': str(e)}), 500

@app.route('/almacenes/<int:almacen_id>', methods=['PUT'])
def actualizar_almacen(almacen_id):
    data = request.get_json()
    name = data.get('name')
    location = data.get('location')
    capacity = data.get('capacity')
    image = data.get('image')

    try:
        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()
        c.execute("""
            UPDATE almacenes
            SET name = ?, location = ?, capacity = ?, image = ?
            WHERE id = ?
        """, (name, location, capacity, image, almacen_id))
        conn.commit()
        conn.close()

        return jsonify({'message': 'Almacén actualizado correctamente'}), 200
    except sqlite3.IntegrityError:
        conn.close()
        return jsonify({'error': 'Ubicación duplicada'}), 409
    except Exception as e:
        conn.close()
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
        conn.close()
        return jsonify({'error': str(e)}), 500

@app.route('/almacenes/<int:almacen_id>', methods=['DELETE'])
def eliminar_almacen(almacen_id):
    try:
        conn = sqlite3.connect(DataBase_NAME)
        c = conn.cursor()
        c.execute("DELETE FROM almacenes WHERE id = ?", (almacen_id,))
        conn.commit()
        conn.close()

        return jsonify({'message': 'Almacén eliminado correctamente'}), 200

    except Exception as e:
        conn.close()
        return jsonify({'error': str(e)}), 500

if __name__ == "__main__":
    init_db()   # <--- AQUÍ SE CREA LA BD Y LA TABLA
    app.run(host="0.0.0.0", port=5000, debug=True)