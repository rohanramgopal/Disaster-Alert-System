from flask import Flask, request, jsonify
from flask_cors import CORS
import sqlite3
from datetime import datetime

app = Flask(__name__)
CORS(app)

DATABASE = "disaster.db"


def get_db_connection():
    conn = sqlite3.connect(DATABASE)
    conn.row_factory = sqlite3.Row
    return conn


def init_db():
    conn = get_db_connection()
    cursor = conn.cursor()

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS disasters (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            disaster_type TEXT NOT NULL,
            description TEXT NOT NULL,
            severity TEXT NOT NULL,
            latitude REAL NOT NULL,
            longitude REAL NOT NULL,
            area_name TEXT NOT NULL,
            status TEXT DEFAULT 'Active',
            created_at TEXT NOT NULL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS volunteers (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            phone TEXT NOT NULL,
            skill TEXT NOT NULL,
            availability TEXT NOT NULL,
            latitude REAL,
            longitude REAL,
            created_at TEXT NOT NULL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS resources (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            resource_name TEXT NOT NULL,
            quantity INTEGER NOT NULL,
            contact_person TEXT NOT NULL,
            contact_phone TEXT NOT NULL,
            latitude REAL,
            longitude REAL,
            created_at TEXT NOT NULL
        )
    """)

    conn.commit()
    conn.close()


@app.route("/api/disasters", methods=["GET"])
def get_disasters():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM disasters ORDER BY id DESC").fetchall()
    conn.close()
    return jsonify([dict(row) for row in rows])


@app.route("/api/disasters", methods=["POST"])
def add_disaster():
    data = request.get_json()

    required_fields = ["disaster_type", "description", "severity", "latitude", "longitude", "area_name"]
    for field in required_fields:
        if field not in data or data[field] in ["", None]:
            return jsonify({"error": f"{field} is required"}), 400

    conn = get_db_connection()
    conn.execute("""
        INSERT INTO disasters (disaster_type, description, severity, latitude, longitude, area_name, created_at)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, (
        data["disaster_type"],
        data["description"],
        data["severity"],
        float(data["latitude"]),
        float(data["longitude"]),
        data["area_name"],
        datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    ))
    conn.commit()
    conn.close()

    return jsonify({"message": "Disaster added successfully"}), 201


@app.route("/api/volunteers", methods=["GET"])
def get_volunteers():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM volunteers ORDER BY id DESC").fetchall()
    conn.close()
    return jsonify([dict(row) for row in rows])


@app.route("/api/volunteers", methods=["POST"])
def add_volunteer():
    data = request.get_json()

    required_fields = ["name", "phone", "skill", "availability"]
    for field in required_fields:
        if field not in data or data[field] in ["", None]:
            return jsonify({"error": f"{field} is required"}), 400

    conn = get_db_connection()
    conn.execute("""
        INSERT INTO volunteers (name, phone, skill, availability, latitude, longitude, created_at)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, (
        data["name"],
        data["phone"],
        data["skill"],
        data["availability"],
        float(data["latitude"]) if data.get("latitude") not in [None, ""] else None,
        float(data["longitude"]) if data.get("longitude") not in [None, ""] else None,
        datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    ))
    conn.commit()
    conn.close()

    return jsonify({"message": "Volunteer added successfully"}), 201


@app.route("/api/resources", methods=["GET"])
def get_resources():
    conn = get_db_connection()
    rows = conn.execute("SELECT * FROM resources ORDER BY id DESC").fetchall()
    conn.close()
    return jsonify([dict(row) for row in rows])


@app.route("/api/resources", methods=["POST"])
def add_resource():
    data = request.get_json()

    required_fields = ["resource_name", "quantity", "contact_person", "contact_phone"]
    for field in required_fields:
        if field not in data or data[field] in ["", None]:
            return jsonify({"error": f"{field} is required"}), 400

    conn = get_db_connection()
    conn.execute("""
        INSERT INTO resources (resource_name, quantity, contact_person, contact_phone, latitude, longitude, created_at)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, (
        data["resource_name"],
        int(data["quantity"]),
        data["contact_person"],
        data["contact_phone"],
        float(data["latitude"]) if data.get("latitude") not in [None, ""] else None,
        float(data["longitude"]) if data.get("longitude") not in [None, ""] else None,
        datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    ))
    conn.commit()
    conn.close()

    return jsonify({"message": "Resource added successfully"}), 201


@app.route("/api/stats", methods=["GET"])
def get_stats():
    conn = get_db_connection()

    disaster_count = conn.execute("SELECT COUNT(*) AS count FROM disasters").fetchone()["count"]
    volunteer_count = conn.execute("SELECT COUNT(*) AS count FROM volunteers").fetchone()["count"]
    resource_count = conn.execute("SELECT COUNT(*) AS count FROM resources").fetchone()["count"]

    conn.close()

    return jsonify({
        "disasters": disaster_count,
        "volunteers": volunteer_count,
        "resources": resource_count
    })


if __name__ == "__main__":
    init_db()
    app.run(debug=True)