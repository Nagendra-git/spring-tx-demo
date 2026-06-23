package com.example.txdemo.utils;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation indicating that a class serves as a <em>Facade</em> in the
 * application architecture.
 *
 * <p>A Facade acts as a simplified interface over one or more complex subsystems
 * (services, repositories, external clients, etc.), coordinating their interactions
 * and presenting a coarser-grained API to callers such as controllers or other
 * facades.
 *
 * <p>This annotation is a specialization of {@link org.springframework.stereotype.Component @Component},
 * so every class annotated with {@code @Facade} is automatically registered as a
 * Spring bean and is eligible for component scanning, dependency injection, and all
 * standard Spring bean post-processing.
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * @Facade
 * public class OrderFacade {
 *
 *     private final OrderService    orderService;
 *     private final PaymentService  paymentService;
 *     private final InventoryClient inventoryClient;
 *
 *     // constructor injection …
 *
 *     public OrderConfirmation placeOrder(PlaceOrderRequest request) {
 *         inventoryClient.reserve(request.items());
 *         Order order = orderService.create(request);
 *         paymentService.charge(order);
 *         return OrderConfirmation.from(order);
 *     }
 * }
 * }</pre>
 *
 * <h2>Naming the bean explicitly</h2>
 * <p>An optional {@link #value()} attribute mirrors the one on {@code @Component},
 * allowing a custom bean name to be specified:
 * <pre>{@code
 * @Facade("myOrderFacade")
 * public class OrderFacade { … }
 * }</pre>
 *
 * <h2>Architectural guidelines</h2>
 * <ul>
 *   <li>Facades should <strong>not</strong> contain business logic; delegate to
 *       dedicated service or domain classes instead.</li>
 *   <li>Facades are typically consumed by the <em>presentation layer</em>
 *       (controllers, GraphQL resolvers, event listeners) and should not call
 *       each other in a way that creates circular dependencies.</li>
 *   <li>Transaction boundaries, security checks, and cross-cutting concerns belong
 *       in the underlying services, not in the facade.</li>
 * </ul>
 *
 * @see org.springframework.stereotype.Component
 * @see org.springframework.stereotype.Service
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Facade {

    /**
     * The name of the Spring bean, if a non-default name is required.
     *
     * <p>Aliased to {@link org.springframework.stereotype.Component#value()}.
     * When left empty (the default), the bean name is derived from the
     * simple class name with its first letter lower-cased, following the
     * standard Spring naming convention.
     *
     * @return the explicit bean name, or an empty string to use the default
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}