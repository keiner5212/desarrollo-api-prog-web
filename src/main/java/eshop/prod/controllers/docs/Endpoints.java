package eshop.prod.controllers.docs;

import java.util.HashMap;
import java.util.Map;

public class Endpoints {
    public static final Map<String, Object> AUTH_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> CUSTOMERS_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> ORDER_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> ORDER_ITEM_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> PAYMENT_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> PRODUCT_ENDPOINTS = new HashMap<>();
    public static final Map<String, Object> SHIPMENT_ENDPOINTS = new HashMap<>();

    static  {
        // auth
        AUTH_ENDPOINTS.put("POST /api/auth/register", "Register a new user");
        AUTH_ENDPOINTS.put("POST /api/auth/login",
                "Login with your credentials (you will receive a token, use it in the 'Authorization' header of Bearer type)");

        // customers
        CUSTOMERS_ENDPOINTS.put("GET /api/v1/customers", "Get all customers");
        CUSTOMERS_ENDPOINTS.put("GET /api/v1/customers/{id}", "Get a customer by id");
        CUSTOMERS_ENDPOINTS.put("GET /api/v1/customers/email/{email}", "Get a customer by email");
        CUSTOMERS_ENDPOINTS.put("GET /api/v1/customers/address/{address}", "Get customers by address");
        CUSTOMERS_ENDPOINTS.put("GET /api/v1/customers/name/{name}", "Get customers by name");
        CUSTOMERS_ENDPOINTS.put("PUT /api/v1/customers/{id}", "Update a customer");
        CUSTOMERS_ENDPOINTS.put("DELETE /api/v1/customers/{id}", "Delete a customer");

        // orders
        ORDER_ENDPOINTS.put("GET /api/v1/orders", "Get all orders");
        ORDER_ENDPOINTS.put("GET /api/v1/orders/{id}", "Get an order by id");
        ORDER_ENDPOINTS.put("GET /api/v1/orders/{customer_id}", "Get orders by customer id");
        ORDER_ENDPOINTS.put("GET /api/v1/orders/date-range?startDate={startDate}&endDate={endDate}",
                "Get orders by date range");
        ORDER_ENDPOINTS.put("GET /customer/{id}/status/{status}", "Get orders by status");
        ORDER_ENDPOINTS.put("POST /api/v1/orders", "Create an order");
        ORDER_ENDPOINTS.put("PUT /api/v1/orders/{id}", "Update an order");
        ORDER_ENDPOINTS.put("DELETE /api/v1/orders/{id}", "Delete an order");

        // order items
        ORDER_ITEM_ENDPOINTS.put("GET /api/v1/order-items", "Get all order items");
        ORDER_ITEM_ENDPOINTS.put("GET /api/v1/order-items/{id}", "Get an order item by id");
        ORDER_ITEM_ENDPOINTS.put("GET /api/v1/order-items/order/{id}", "Get order items by order id");
        ORDER_ITEM_ENDPOINTS.put("GET /api/v1/order-items/product/{id}", "Get order items by product id");
        ORDER_ITEM_ENDPOINTS.put("GET /api/v1/order-items/product/{id}/total-sell", "Get total sell by product id");
        ORDER_ITEM_ENDPOINTS.put("POST /api/v1/order-items", "Create an order item");
        ORDER_ITEM_ENDPOINTS.put("PUT /api/v1/order-items/{id}", "Update an order item");
        ORDER_ITEM_ENDPOINTS.put("DELETE /api/v1/order-items/{id}", "Delete an order item");

        // payments
        PAYMENT_ENDPOINTS.put("GET /api/v1/payments", "Get all payments");
        PAYMENT_ENDPOINTS.put("GET /api/v1/payments/{id}", "Get a payment by id");
        PAYMENT_ENDPOINTS.put("GET /api/v1/payments/order/{id}/payment-method/{paymentMethod}",
                "Get payments by order id and payment method");
        PAYMENT_ENDPOINTS.put("GET /api/v1/payments/date-range?startDate={startDate}&endDate={endDate}",
                "Get payments by date range");
        PAYMENT_ENDPOINTS.put("POST /api/v1/payments", "Create a payment");
        PAYMENT_ENDPOINTS.put("PUT /api/v1/payments/{id}", "Update a payment");
        PAYMENT_ENDPOINTS.put("DELETE /api/v1/payments/{id}", "Delete a payment");

        // products
        PRODUCT_ENDPOINTS.put("GET /api/v1/products", "Get all products");
        PRODUCT_ENDPOINTS.put("GET /api/v1/products/{id}", "Get a product by id");
        PRODUCT_ENDPOINTS.put("GET /api/v1/products/stock", "Get a product by stock greater than zero");
        PRODUCT_ENDPOINTS.put("GET /api/v1/products/search/{search}", "Get products by search");
        PRODUCT_ENDPOINTS.put("GET /api/v1/products/price-stock/{price}/{stock}",
                "Get products by price and stock (less than)");
        PRODUCT_ENDPOINTS.put("POST /api/v1/products", "Create a product");
        PRODUCT_ENDPOINTS.put("PUT /api/v1/products/{id}", "Update a product");
        PRODUCT_ENDPOINTS.put("DELETE /api/v1/products/{id}", "Delete a product");

        // shipments
        SHIPMENT_ENDPOINTS.put("GET /api/v1/shipments", "Get all shipments");
        SHIPMENT_ENDPOINTS.put("GET /api/v1/shipments/{id}", "Get a shipment by id");
        SHIPMENT_ENDPOINTS.put("GET /api/v1/shipments/order/{id}", "Get shipments by order id");
        SHIPMENT_ENDPOINTS.put("GET /api/v1/shipments/carrier/{nameCarrier}",
                "Get shipments by carrier name");
        SHIPMENT_ENDPOINTS.put("POST /api/v1/shipments", "Create a shipment");
        SHIPMENT_ENDPOINTS.put("PUT /api/v1/shipments/{id}", "Update a shipment");
        SHIPMENT_ENDPOINTS.put("DELETE /api/v1/shipments/{id}", "Delete a shipment");
    }
}
