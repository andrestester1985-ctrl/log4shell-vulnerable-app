from pytm import TM, Server, Datastore, Dataflow, Boundary

tm = TM("Log4Shell Vulnerable API Model")
tm.description = "Modelo de amenazas para API PCI"

# Límites
internet = Boundary("Internet")
trust_zone = Boundary("Trusted Network")

# Activos
user = Server("User Browser")
user.in_boundary = internet

api_service = Server("Vulnerable API Service")
api_service.in_boundary = trust_zone
api_service.is_web_server = True

db = Datastore("PCI Database")
db.in_boundary = trust_zone

# Flujos
Dataflow(user, api_service, "HTTPS Traffic")
Dataflow(api_service, db, "SQL Connection")

# Esto es lo más importante
tm.process()