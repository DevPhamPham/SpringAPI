### Dữ liệu test POSTMAN:

POST /api/account/register:

    {
    "email": "52100901@student.tdtu.edu.vn",
    "firstName": "Pham",
    "lastName": "khoa",
    "password": "khoa1303"
    }

POST /api/account/login:

    {
    "email": "52100901@student.tdtu.edu.vn",
    "password": "khoa1303"
    }

    {
    "email": "52100901@student.tdtu.edu.vn",
    "password": "khoa13031"
    }

GET /api/products:

    http://localhost:8080/api/products

POST /api/products: (add new products) phải đăng nhập bằng email và password (basic auth)

    {
    "code": "P001",
    "name": "Product 1",
    "price": 10.99,
    "illustration": "https://example.com/images/product1.jpg",
    "description": "This is a sample product description."
    }

GET /api/products/{id}:

    http://localhost:8080/api/products/1

PUT /api/products/{id}:

    {
    "code": "P001",
    "name": "Product 1 update",
    "price": 10.99,
    "illustration": "https://example.com/images/product1.jpg",
    "description": "This is a sample product description."
    }

PATCH /api/products/{id}:

    {
    "name": "new product name",
    "price": 99.99
    }

DELETE /api/products/{id}:

    http://localhost:8080/api/products/1

GET /api/orders:

    http://localhost:8080/api/orders


POST /api/orders:

    {
        "orderNumber": "ORD12345",
        "totalSellingPrice": 100.0,
        "productList": [
            {
            "code": "PROD001",
            "name": "Product 1",
            "price": 50.0,
            "illustration": "https://example.com/product1.jpg",
            "description": "This is product 1"
            },
            {
            "code": "PROD002",
            "name": "Product 2",
            "price": 50.0,
            "illustration": "https://example.com/product2.jpg",
            "description": "This is product 2"
            }
        ]
    }

GET /api/orders/{id}:

    http://localhost:8080/api/orders/1

PUT /api/orders/{id}:


DELETE /api/orders/{id}:
