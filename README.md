## How To Start Service

##### Preparing MySQL Database
```bash
docker run --name ampos-mysql8.0 \
	-p 3306:3306 \
	-e MYSQL_ROOT_PASSWORD=ampos \
	-e MYSQL_DATABASE=ampos \
	-e MYSQL_USER=ampos \
	-e MYSQL_PASSWORD=ampos \
	-d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
##### Installing sbt for Play Framework on Mac
```bash
brew install sbt@1
```
* Note: [Installing sbt on different Operating systems.](https://www.scala-sbt.org/1.0/docs/Setup.html)

##### Starting Service
```bash
sbt run
```

## APIs Description
### Menu Management

#### 1. Adding a food to menu
```
POST localhost:9000/foods
```
###### Request Payload Example
```json
{
    "name": "Banana milkshake",
    "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
    "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
    "price": 60,
    "types": [
        "Banana",
        "Milk"
    ]
}
```
###### Response Example
```
Status 201
```
```json
{
    "id": 21,
    "name": "Banana milkshake",
    "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
    "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
    "price": 60,
    "types": [
        "Banana",
        "Milk"
    ]
}
```

#### 2. Getting the restaurant menu which supports keyword search and pagination
```
GET localhost:9000/foods?keyword={keyword}&page={page}&size={size}
```
* Note: Query Strings are not mandatory.

###### Example
```
GET localhost:9000/foods?keyword=Banana
```
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
            "types": [
                "Milk",
                "Banana"
            ]
        }
    ]
}
```
#### 3. Updating an existing food
```
PUT localhost:9000/foods/{foodId}
```
###### Request Payload Example
```
{
    "name": "Banana milkshake",
    "description": "Spiced Banana Milkshake – This deliciously thick banana milkshake is so full of flavour, and is perfectly spiced with a hint of cinnamon and ginger. A perfect, indulgent pick-me-up drink!",
    "image": "https://brainfoodstudio.com/wp-content/uploads/2017/06/dairy-free-banana-peanut-butter-shake-2.jpg",
    "price": 60,
    "types": [
        "Banana",
        "Milk"
    ]
}
```
###### Response Example
```
Status 200
```
#### 4. Deleting an existing food
``` 
DELETE localhost:9000/foods/{foodId}
```
###### Response Example
```
Status 200
```
### Bill Order Management
#### 1. Creating a bill
```
POST localhost:9000/bills
```
###### Request Payload Example
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
###### Response Example
```
Status 201
```
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
#### 2. Retrieving all bills and order items
```
GET localhost:9000/bills
```
###### Response Example
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
#### 3. Getting bill information for check operation
```
GET localhost:9000/bills/{billId}/check
```
###### Response Example

```json
{
    "id": 14,
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
```

#### 4. Updating an existing bill which allows addition and removal of order items and quantities
```
PUT localhost:9000/bills/{billId}
```
###### Request Payload Example
```json
{
    "newOrders":[{"id":15, "quantity": 1}, {"id":19, "quantity": 3}],
    "orders": [
        {
            "foodName": "Chocolate",
            "quantity": 5
        }
    ]
}
```
* Note: The behavior of new addition of order items is like creating bill API.
* Note: If you don't want to update any existing order items, the same values need to be passed.
* Note: `foodName` is used as a part of primary key for snapshotting foods of the menu. So future modification of foods won't influence the result of the past bill.
