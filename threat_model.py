from pytm import TM, Server, Datastore, Dataflow, Boundary, Actor

tm = TM("PCI-DSS Compliant Threat Model")
tm.description = "Análisis de amenazas para entorno CDE - Cumplimiento PCI"

# --- LÍMITES DE SEGURIDAD (Segmentación PCI) ---
internet = Boundary("Public Internet")
dmz = Boundary("DMZ (Public Facing)")
cde = Boundary("CDE (Cardholder Data Environment)") # Zona más protegida

# --- ACTORES Y ACTIVOS ---
user = Actor("Customer")

# Servidor de API en la DMZ
api_service = Server("Payment API Gateway")
api_service.in_boundary = dmz
api_service.is_web_server = True
api_service.on_misuse = "Fraude en transacciones y fuga de PAN"

# Base de datos en el CDE (Aislada)
card_db = Datastore("Cardholder Data Repository")
card_db.in_boundary = cde
card_db.is_encrypted = True  # Requisito PCI 3.4
card_db.stores_sensitive_data = True
card_db.description = "Almacena PAN, fecha de expiración y nombres"

# --- FLUJOS DE DATOS (PCI Data Flows) ---
# Flujo 1: El usuario envía datos de tarjeta
f1 = Dataflow(user, api_service, "Transacción de Pago (PAN/CVV)")
f1.protocol = "HTTPS"
f1.dst_port = 443

# Flujo 2: La API procesa y guarda en el CDE (Cruzando frontera de confianza)
f2 = Dataflow(api_service, card_db, "Persistencia de Datos Encriptados")
f2.protocol = "TLS 1.2"
f2.notes = "Cumple con requisito PCI de cifrado en tránsito"

tm.process()