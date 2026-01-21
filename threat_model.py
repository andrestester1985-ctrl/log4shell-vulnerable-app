from pytm import TM, Server, Datastore, Dataflow, Boundary

tm = TM("Log4Shell Vulnerable API")
tm.description = "Análisis de amenazas - Caso Log4Shell"

# Definición de arquitectura
internet = Boundary("Internet")
vpc = Boundary("VPC Trusted")

user = Server("External User")
user.in_boundary = internet

api = Server("Spring Boot API")
api.in_boundary = vpc
api.is_web_server = True

db = Datastore("User Database")
db.in_boundary = vpc
db.is_encrypted = False

# Conexiones
Dataflow(user, api, "HTTPS (Potential Log4j Payload)")
Dataflow(api, db, "SQL connection")

# Ejecución
tm.process()