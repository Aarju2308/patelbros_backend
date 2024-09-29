
# PatelBros

## Description
**PatelBros** is an e-commerce platform built with **Java Spring Boot**, providing features for managing user addresses, processing payments, and handling orders. The backend handles user authentication, address management, and payment integration using **PayPal**, with **MySQL** as the database and **Spring Security** for securing APIs.


## Technologies
- **Java 17**: The core programming language used for the backend.
- **Spring Boot 3.2.6**: For building the backend APIs and managing application components.
  - **Spring Data JPA**: For database interaction and ORM functionality.
  - **Spring Security**: For managing authentication and authorization.
  - **Spring Boot Validation**: For input validation.
- **PayPal SDK**: For payment processing and integration.
- **MySQL**: The relational database used to store application data.
- **Maven**: For dependency management and building the project.
- **JUnit and Spring Boot Test**: For unit and integration testing.


---

[Frontend Project Documentation](https://github.com/Aarju2308/patelbros_frontend) includes detailed information on the frontend's Technologies, Installation and Project Structure.

---

## Installation

### Prerequisites
Make sure you have the following installed:
- Java 17
- Maven
- Docker
- MySQL (optional, if not using Docker)

## Clone the Repository
```bash
git clone https://github.com/Aarju2308/patelbros_backend.git
cd patelbros_backend
```

## Set Up Docker for MySQL
Ensure Docker is running and set up the MySQL container:
```bash
docker-compose up -d
```
This will pull the MySQL image and run the database container as per the `docker-compose.yml` file.

## Configure Environment Variables
Create an `.env` file in the root directory with the following content:

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/patelbros
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password

# PayPal Configuration
PAYPAL_CLIENT_ID=YOUR_CLIENT_ID
PAYPAL_CLIENT_SECRET=YOUR_CLIENT_SECRET

# JWT Configuration
JWT_SECRET=YOUR_JWT_SECRET

# Server Port
SERVER_PORT=1323
```

## Running the Application
Once dependencies are installed and the database is running (via Docker), start the Spring Boot application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## API Endpoints

### User API Endpoints
- `GET /user/` - Fetches details of the authenticated user.
- `PUT /user/` - Updates details of the authenticated user.
- `POST /user/profile` - Uploads a profile image for the authenticated user (Multipart form data).
- `POST /user/background` - Uploads a background image for the authenticated user (Multipart form data).

### Admin API Endpoints

#### Categories
- `POST /admin/category` - Create a new category.
- `GET /admin/category/{catId}` - Retrieve a specific category.
- `PUT /admin/category/{catId}` - Update an existing category.
- `DELETE /admin/category/{catId}` - Delete a category.

#### Subcategories
- `POST /admin/subCategory` - Create a new subcategory.
- `GET /admin/subCategory/{subCatId}` - Retrieve a specific subcategory.
- `PUT /admin/subCategory/{subCatId}` - Update an existing subcategory.
- `DELETE /admin/subCategory/{subCatId}` - Delete a subcategory.

#### Third Categories
- `POST /admin/thirdCategory` - Create a new third category.
- `GET /admin/thirdCategory/{thirdCatId}` - Retrieve a specific third category.
- `PUT /admin/thirdCategory/{thirdCatId}` - Update an existing third category.
- `DELETE /admin/thirdCategory/{thirdCatId}` - Delete a third category.

#### Brands
- `POST /admin/brand` - Create a new brand.
- `GET /admin/brand` - Retrieve all brands.
- `GET /admin/brand/{brandId}` - Retrieve a specific brand.
- `PUT /admin/brand/{brandId}` - Update an existing brand.
- `DELETE /admin/brand/{brandId}` - Delete a brand.
- `POST /admin/logo/{brandId}` - Upload a brand logo (Multipart form data).

#### Products
- `POST /admin/product` - Create a new product.
- `GET /admin/product/{productId}` - Retrieve a specific product.
- `PUT /admin/product/{productId}` - Update an existing product.
- `DELETE /admin/product/{productId}` - Delete a product.
- `POST /admin/productImage/{productId}` - Upload a product image (Multipart form data).

#### Countries
- `GET /admin/country/{id}` - Retrieve a specific country.
- `PUT /admin/country/{id}` - Update an existing country.
- `DELETE /admin/country/{id}` - Delete a country.

#### Users
- `GET /admin/users/` - Retrieve all users.
- `PUT /admin/users/{userId}` - Update a specific user.

#### Orders
- `GET /admin/orders` - Retrieve all orders.
- `PUT /admin/order/{orderId}` - Update a specific order.

### User Address Endpoints
- `GET /user/address/` - Get all addresses for the authenticated user.
- `GET /user/address/{addressId}` - Get a specific address.
- `POST /user/address/` - Add a new address.
- `PUT /user/address/{addressId}` - Update an existing address.
- `DELETE /user/address/{addressId}` - Delete a specific address.

### Auth API Endpoints
- `POST /auth/register` - Register a new user.
- `POST /auth/login` - Authenticate a user.
- `POST /auth/verify` - Verify a user.

### Public API Endpoints
- `GET /public/category` - Retrieve all categories.
- `GET /public/subCategory` - Retrieve all subcategories.
- `GET /public/subCategoryByCategory/{catId}` - Get subcategories by category ID.
- `GET /public/thirdCategory` - Retrieve all third categories.
- `GET /public/thirdCategoryBySubCategory/{subCatId}` - Get third categories by subcategory ID.
- `GET /public/brands` - Retrieve all brands.
- `GET /public/product` - Retrieve all products.
- `GET /public/product/{thirdCatId}` - Get products by third category ID.
- `GET /public/productsBySubCategory/{subCatId}` - Get products by subcategory ID.
- `GET /public/searchProducts` - Search for products.
- `GET /public/singleProduct/{productId}` - Get a single product.

### Cart API Endpoints
- `POST /public/cart` - Add an item to the cart.
- `GET /public/countCart` - Get the count of items in the cart.
- `GET /public/cart` - Retrieve the cart contents.
- `POST /public/removeFromCart` - Remove an item from the cart.
- `DELETE /public/cart/{cartId}` - Delete a specific item from the cart.

### User Order Endpoints
- `GET /user/order/` - Get all orders for the authenticated user.
- `GET /user/order/{billNo}` - Get a specific order by bill number.
- `POST /user/order/payForOrder` - Pay for an order.
- `POST /user/order/` - Create a new order.
- `GET /user/paymentSuccess` - Confirm payment success.


---

## Configuration

### Docker Configuration
- The `docker-compose.yml` file is configured to set up a **MySQL** container automatically.
- To stop the Docker containers:
  ```bash
  docker-compose down
  ```

### Database Configuration
- You can find the database properties in `src/main/resources/application.properties` or configure them via environment variables, such as in the `.env` file mentioned above.

---

## Testing
Run the unit tests and integration tests with the following command:
```bash
mvn test
```
---

## Contributing
If you'd like to contribute, please follow these guidelines:
1. Fork the repository and create a feature branch.
2. Ensure code adheres to existing code formatting and conventions.
3. Run tests before submitting a PR.
4. Submit a Pull Request with a detailed description of the changes.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

## Contact
For any inquiries or issues, feel free to reach out at `patelbros_support@example.com`.

---
