package com.example.job3.utils;

import com.example.job3.dto.ProductDto;
import com.example.job3.dto.UserDto;
import com.example.job3.entity.ProductEntity;
import com.example.job3.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {
    public ModelConverter() {
    }

    public static UserDto toUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .uuid(userEntity.getUuid())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .age(userEntity.getAge())
                .build();
    }

    public static UserEntity toUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .uuid(userDto.getUuid())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
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


    public static ProductDto toProductDto(ProductEntity productEntity) {
        return ProductDto.builder()
                .uuid(productEntity.getUuid())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .build();
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
        }



//    public static CategoryDto toCategoryDto(CategoryEntity categoryEntity) {
//        return CategoryDto.builder()
//                .uuid(categoryEntity.getUuid())
//                .name(categoryEntity.getName())
//                .description(categoryEntity.getDescription())
//                .build();
//    }
//
//    public static CategoryEntity toCategoryEntity(CategoryDto categoryDto) {
//        return CategoryEntity.builder()
//                .uuid(categoryDto.getUuid())
//                .name(categoryDto.getName())
//                .description(categoryDto.getDescription())
//                .build();
//    }
//
//    public static CategoryEntity toCreateCategoryEntity(CreateCategoryDto categoryDto) {
//        return CategoryEntity.builder()
//                .uuid(categoryDto.getUuid())
//                .name(categoryDto.getName())
//                .description(categoryDto.getDescription())
//                .build();
//    }
//
//    public static List<CategoryDto> toCategoryDtoList(List<CategoryEntity> categoryEntityList) {
//        return categoryEntityList.stream()
//                .map(ModelConverter::toCategoryDto)
//                .collect(Collectors.toList());
//    }
//
//    public static List<CategoryEntity> toCategoryEntityList(List<CategoryDto> categoryDtoList) {
//        return categoryDtoList.stream()
//                .map(ModelConverter::toCategoryEntity)
//                .collect(Collectors.toList());
//    }
//
//    public static BasketDto toBasketDto(BasketEntity basketEntity) {
//        return BasketDto.builder()
//                .uuid(basketEntity.getUuid())
//                .createdAt(basketEntity.getCreatedAt())
//                .updatedAt(basketEntity.getUpdatedAt())
//                .build();
//    }
//
//    public static BasketEntity toBasketEntity(BasketDto basketDto) {
//        return BasketEntity.builder()
//                .uuid(basketDto.getUuid())
//                .createdAt(basketDto.getCreatedAt())
//                .updatedAt(basketDto.getUpdatedAt())
//                .build();
//    }
//
//    public static BasketEntity toCreateBasketEntity(BasketDto basketDto) {
//        return BasketEntity.builder()
//                .uuid(basketDto.getUuid())
//                .createdAt(basketDto.getCreatedAt())
//                .updatedAt(basketDto.getUpdatedAt())
//                .build();
//    }
//
//    public static List<BasketDto> toBasketDtoList(List<BasketEntity> basketEntityList) {
//        return basketEntityList.stream()
//                .map(ModelConverter::toBasketDto)
//                .collect(Collectors.toList());
//    }
//
//    public static List<BasketEntity> toBasketEntityList(List<BasketDto> basketDtoList) {
//        return basketDtoList.stream()
//                .map(ModelConverter::toBasketEntity)
//                .collect(Collectors.toList());
//    }
//}