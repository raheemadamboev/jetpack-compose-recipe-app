package com.example.composerecipeapp.helper.mapper

import com.example.composerecipeapp.model.RecipeModel
import com.example.composerecipeapp.model.RecipeDtoModel

class RecipeDtoMapper : DomainMapper<RecipeDtoModel, RecipeModel> {

    override fun mapToDomainModel(model: RecipeDtoModel) =
        RecipeModel(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstructions = model.cookingInstructions,
            ingredients = model.ingredients ?: listOf(),
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated
        )

    override fun mapFromDomainModel(domainModel: RecipeModel) = RecipeDtoModel(
        pk = domainModel.id,
        title = domainModel.title,
        publisher = domainModel.publisher,
        featuredImage = domainModel.featuredImage,
        rating = domainModel.rating,
        sourceUrl = domainModel.sourceUrl,
        description = domainModel.description,
        cookingInstructions = domainModel.cookingInstructions,
        ingredients = domainModel.ingredients,
        dateAdded = domainModel.dateAdded,
        dateUpdated = domainModel.dateUpdated
    )

    fun toDomainList(initial: List<RecipeDtoModel>): List<RecipeModel> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<RecipeModel>): List<RecipeDtoModel> {
        return initial.map { mapFromDomainModel(it) }
    }
}