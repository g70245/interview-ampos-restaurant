# Restaurant Management System

A restaurant management system built with Play Framework that handles menu and bill order management.

## Features
- Menu Management (CRUD operations)
- Bill Order Management
- Search functionality with pagination
- Food categorization by types
- Real-time bill calculations

## System Architecture

### Class Diagram
Shows the core domain models and their relationships:
![Imgur](https://i.imgur.com/P2QmnH2.png)

### Database Schema
Entity-Relationship diagram representing the database structure:
![Imgur](https://i.imgur.com/30ZjaY2.png)

## Getting Started

### Prerequisites
- Docker
- MySQL 8.0
- sbt 1.x
- JDK (version should be specified)

### Installation

1. **Set up MySQL Database**
```bash
docker run --name ampos-mysql8.0 \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD=ampos \
    -e MYSQL_DATABASE=ampos \
    -e MYSQL_USER=ampos \
    -e MYSQL_PASSWORD=ampos \
    -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

2. **Install sbt**
   - On macOS:
     ```bash
     brew install sbt@1
     ```
   - For other operating systems, follow the [official sbt installation guide](https://www.scala-sbt.org/1.0/docs/Setup.html)

3. **Start the Service**
```bash
sbt run
```
The service will be available at `localhost:9000`

## API Documentation

### Menu Management API

#### 1. Create Food Item
- **Endpoint**: `POST /foods`
- **Description**: Add a new food item to the menu
- **Request Body**:
```json
{
    "name": "Banana milkshake",
    "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
    "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
    "price": 60,
    "types": ["Banana", "Milk"]
}
```
- **Response**: `201 Created`
```json
{
    "id": 21,
    "name": "Banana milkshake",
    "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
    "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
    "price": 60,
    "types": ["Banana", "Milk"]
}
```

#### 2. Get Menu Items
- **Endpoint**: `GET /foods?keyword={keyword}&page={page}&size={size}`
- **Description**: Retrieve menu items with optional search and pagination
- **Query Parameters**: All parameters are optional
- **Response Example**:
```json
{
    "keyword": "Banana",
    "total": 1,
    "foods": [
        {
            "id": 21,
            "name": "Banana milkshake",
            "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
            "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
            "price": 60,
            "types": ["Milk", "Banana"]
        }
    ]
}
```

#### 3. Update Food Item
- **Endpoint**: `PUT /foods/{foodId}`
- **Description**: Update an existing food item
- **Request Body**: Same as Create Food Item
- **Response**: `200 OK`

#### 4. Delete Food Item
- **Endpoint**: `DELETE /foods/{foodId}`
- **Description**: Remove a food item from the menu
- **Response**: `200 OK`

### Bill Order Management API

#### 1. Create Bill
- **Endpoint**: `POST /bills`
- **Request Body**:
```json
{
    "newOrders": [
        {
            "id": 21,
            "quantity": 3
        }
    ]
}
```
- **Response**: `201 Created`
```json
{
    "id": 13,
    "orderedTime": "05/08/2019 05:31:18",
    "orders": [
        {
            "foodName": "Banana milkshake",
            "unitPrice": 60,
            "quantity": 3
        }
    ],
    "totalPrice": 180
}
```

#### 2. Get All Bills
- **Endpoint**: `GET /bills`
- **Description**: Retrieve all bills with their order items
- **Response Example**:
```json
[
    {
        "id": 13,
        "orderedTime": "05/07/2019 21:38:48",
        "orders": [
            {
                "foodName": "Banana milkshake",
                "unitPrice": 60,
                "quantity": 3
            }
        ],
        "totalPrice": 180
    }
]
```

#### 3. Get Bill Information
- **Endpoint**: `GET /bills/{billId}/check`
- **Description**: Get detailed information for a specific bill
- **Response**: Same format as Create Bill response

#### 4. Update Bill
- **Endpoint**: `PUT /bills/{billId}`
- **Description**: Update bill with new orders or modify existing ones
- **Request Body**:
```json
{
    "newOrders": [
        {"id": 15, "quantity": 1},
        {"id": 19, "quantity": 3}
    ],
    "orders": [
        {
            "foodName": "Chocolate",
            "quantity": 5
        }
    ]
}
```

**Important Notes**:
- To avoid updating existing order items, pass the same values as before
- Food name is used as part of the primary key for menu item snapshots
- Future menu modifications won't affect past bills

## Development Status

### Completed Features
- ✅ Menu Management (CRUD operations)
- ✅ Bill Order Management
- ✅ Basic API Implementation

### In Progress
- ⏳ Enhanced Exception Handling
  - Currently returns Status 400 for all exceptions
  - Planned: Implement specific error codes and messages

## Technical Notes
- Food items in bills are snapshotted using the food name as part of the primary key
- Menu item modifications don't affect historical bills
- All API endpoints return JSON responses
- Pagination is optional in search endpoints
