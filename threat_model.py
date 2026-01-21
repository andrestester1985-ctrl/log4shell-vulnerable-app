from pytm import TM, Server, Datastore, Dataflow, Boundary

tm = TM("Log4Shell Vulnerable API Model")
tm.description = "Modelo de amenazas para API con vulnerabilidad Log4Shell"

# Definición de Límites (Boundaries)
internet = Boundary("Internet")
trust_zone = Boundary("Trusted Network")

# Elementos de la arquitectura
user = Server("User Browser")
user.in_boundary = internet

api_service = Server("Vulnerable API Service")
api_service.description = "Servicio Java Spring Boot vulnerable a Log4Shell"
api_service.in_boundary = trust_zone
api_service.is_web_server = True

db = Datastore("PCI Database")
db.in_boundary = trust_zone
db.is_encrypted = False # Esto generará una amenaza automáticamente

# Flujos de datos (Dataflows)
Dataflow(user, api_service, "HTTPS Traffic (Log4j Payload)")
Dataflow(api_service, db, "SQL Query / Storage")

# Generar el reporte
tm.process()