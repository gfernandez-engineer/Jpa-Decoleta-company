# Sunat-Consulta

Microservicio que consulta RUCs en la API de Decolecta (SUNAT), persiste los resultados y expone endpoints REST propios.

## Cómo correr el proyecto

### 1. Configurar el token de Decolecta

El token **nunca está hardcodeado**, solo para el fin de esta eval se considerara en el properties. 

### 2. Compilar y levantar

```bash
mvn spring-boot:run
```

Verificar la app en: `http://localhost:8081`

### 3. Consola H2 (opcional)

Puedes ver la BD en el navegador mientras la app corre:

- URL: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:sunatdb`
- User: `sa`
- Password: *(vacío)*

---

## Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/sunat/ruc/{ruc}` | Consulta RUC en Decolecta y guarda en BD |
| GET | `/api/sunat/ruc/{ruc}/consultas` | Historial de consultas para ese RUC |

---

## Ejemplos con curl

### RUC válido (SUCCESS)
```bash
curl -X GET http://localhost:8081/api/sunat/ruc/20601030013
```

Respuesta esperada (HTTP 200):
```json
{
  "codigo": 2000,
  "mensaje": "Consulta realizada correctamente",
  "objeto": {
    "id": 1,
    "ruc": "20601030013",
    "razonSocial": "REXTIE S.A.C.",
    "estado": "ACTIVO",
    "condicion": "HABIDO",
    "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC",
    "ubigeo": "150131",
    "departamento": "LIMA",
    "provincia": "LIMA",
    "distrito": "SAN ISIDRO",
    "esAgenteRetencion": false,
    "esBuenContribuyente": false,
    "createdAt": "2026-02-21T10:00:00",
    "consultas": [...]
  },
  "timestamp": "2026-02-21T18:59:39"
}
```

### RUC inválido (ERROR - formato)
```bash
curl -X GET http://localhost:8081/api/sunat/ruc/12345
```

Respuesta esperada (HTTP 400):
```json
{
  "codigo": 4000,
  "mensaje": "RUC debe tener exactamente 11 dígitos",
  "objeto": null,
  "timestamp": "2026-02-21T18:59:39"
}
```

### Historial de consultas
```bash
curl -X GET http://localhost:8081/api/sunat/ruc/20601030013/consultas
```

## Colección Bruno

En la carpeta `bruno/` se encuentran las requests listas para importar en Bruno:

1. `consultar-ruc-valido.bru` — RUC existente (espera SUCCESS)
2. `consultar-ruc-invalido.bru` — RUC con formato incorrecto (espera 400)
3. `historial-consultas.bru` — Historial del RUC consultado


