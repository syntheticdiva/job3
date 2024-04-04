package com.example.job3.utils;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.category.CategoryDto;
import com.example.job3.dto.category.CreateCategoryDto;
import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {
    public ModelConverter() {
    }

    public static UserDto toUserDto(UserEntity userEntity) {
        UserDto.UserDtoBuilder userDtoBuilder = UserDto.builder()
                .uuid(userEntity.getUuid())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .age(userEntity.getAge())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getCreatedAt());

        BasketEntity basketEntity = userEntity.getBasket();
        if (basketEntity != null) {
            userDtoBuilder.basketId(String.valueOf(basketEntity.getUuid()));
        }

        return userDtoBuilder.build();
    }

    public static UserEntity toUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .uuid(userDto.getUuid())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .build();
    }

    public static List<UserDto> toUserDtoList(List<UserEntity> userEntityList) {
        return userEntityList.stream()
                .map(ModelConverter::toUserDto)
                .collect(Collectors.toList());
    }

    public static List<UserEntity> toUserEntityList(List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(ModelConverter::toUserEntity)
                .collect(Collectors.toList());
    }

//    public static ProductDto toProductDto(ProductEntity productEntity) {
//        return ProductDto.builder()
//                .uuid(productEntity.getUuid())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .createdAt(productEntity.getCreatedAt())
//                .updatedAt(productEntity.getUpdatedAt())
//                .categoryId(String.valueOf(productEntity.getCategory().getUuid()))
//                .build();
//    }

    public static ProductDto toProductDto(ProductEntity productEntity) {
        ProductDto.ProductDtoBuilder builder = ProductDto.builder()
                .uuid(productEntity.getUuid())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt());

        CategoryEntity categoryEntity = productEntity.getCategory();
        if (categoryEntity != null) {
            builder.categoryId(String.valueOf(categoryEntity.getUuid()));
        }

        return builder.build();
    }


    public static ProductEntity toProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .uuid(productDto.getUuid())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }


    public static List<ProductEntity> toProductEntityList(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(ModelConverter::toProductEntity)
                .collect(Collectors.toList());
    }


    public static List<ProductDto> toProductDtoList(List<ProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(ModelConverter::toProductDto)
                .collect(Collectors.toList());
    }



    public static CategoryDto toCategoryDto(CategoryEntity categoryEntity) {
        return CategoryDto.builder()
                .uuid(categoryEntity.getUuid())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .build();
    }


    public static CategoryEntity toCategoryEntity(CategoryDto categoryDto) {
        return CategoryEntity.builder()
                .uuid(categoryDto.getUuid())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .createdAt(categoryDto.getCreatedAt())
                .updatedAt(categoryDto.getUpdatedAt())
                .build();
    }

    public static CategoryEntity toCreateCategoryEntity(CreateCategoryDto categoryDto) {
        return CategoryEntity.builder()
                .uuid(categoryDto.getUuid())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
    }

    public static List<CategoryDto> toCategoryDtoList(List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                .map(ModelConverter::toCategoryDto)
                .collect(Collectors.toList());
    }

    public static List<CategoryEntity> toCategoryEntityList(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream()
                .map(ModelConverter::toCategoryEntity)
                .collect(Collectors.toList());
    }


//    public static BasketDto toBasketDto(BasketEntity basketEntity) {
//        return BasketDto.builder()
//                .uuid(basketEntity.getUuid())
//                .createdAt(basketEntity.getCreatedAt())
//                .updatedAt(basketEntity.getUpdatedAt())
//                .build();
//    }
public static BasketDto toBasketDto(BasketEntity basketEntity) {
    if (basketEntity == null) {
        return null; // или другое поведение, в зависимости от требований
    }

    return BasketDto.builder()
            .uuid(basketEntity.getUuid())
            .createdAt(basketEntity.getCreatedAt())
            .updatedAt(basketEntity.getUpdatedAt())
            .build();
}

    public static BasketEntity toBasketEntity(BasketDto basketDto) {
        return BasketEntity.builder()
                .uuid(basketDto.getUuid())
                .createdAt(basketDto.getCreatedAt())
                .updatedAt(basketDto.getUpdatedAt())
                .build();
    }

    public static BasketEntity toCreateBasketEntity(BasketDto basketDto) {
        return BasketEntity.builder()
                .uuid(basketDto.getUuid())
                .createdAt(basketDto.getCreatedAt())
                .updatedAt(basketDto.getUpdatedAt())
                .build();
    }

    public static List<BasketDto> toBasketDtoList(List<BasketEntity> basketEntityList) {
        return basketEntityList.stream()
                .map(ModelConverter::toBasketDto)
                .collect(Collectors.toList());
    }

    public static List<BasketEntity> toBasketEntityList(List<BasketDto> basketDtoList) {
        return basketDtoList.stream()
                .map(ModelConverter::toBasketEntity)
                .collect(Collectors.toList());
    }


    public static OrderDto toOrderDto(OrderEntity orderEntity) {
        OrderDto.OrderDtoBuilder orderDtoBuilder = OrderDto.builder()
                .uuid(orderEntity.getUuid())
                .amount(orderEntity.getAmount())
                .status(orderEntity.getStatus())
                .createdAt(orderEntity.getCreatedAt())
                .updateAt(orderEntity.getUpdatedAt());
        return orderDtoBuilder.build();
    }


    public static OrderEntity toOrderEntity(OrderDto orderDto) {
        return OrderEntity.builder()
                .uuid(orderDto.getUuid())
                .amount(orderDto.getAmount())
                .status(orderDto.getStatus())
                .createdAt(orderDto.getCreatedAt())
                .updatedAt(orderDto.getUpdateAt())
                .build();
    }

    public static OrderEntity CreateOrderEntity(OrderDto orderDto) {
        return OrderEntity.builder()
                .uuid(orderDto.getUuid())
                .amount(orderDto.getAmount())
                .status(orderDto.getStatus())
                .createdAt(orderDto.getCreatedAt())
                .updatedAt(orderDto.getUpdateAt())
                .build();
    }

    public static OrderEntity toCreateOrderEntity(CreateOrderDto createOrderDto) {
        return OrderEntity.builder()
                .uuid(createOrderDto.getUserId())
                .uuid(createOrderDto.getBasketId())
                .status(createOrderDto.getStatus())
                .build();
    }

    public static List<OrderDto> toOrderDtoList(List<OrderEntity> orderEntityList) {
        return orderEntityList.stream()
                .map(ModelConverter::toOrderDto)
                .collect(Collectors.toList());
    }
}




