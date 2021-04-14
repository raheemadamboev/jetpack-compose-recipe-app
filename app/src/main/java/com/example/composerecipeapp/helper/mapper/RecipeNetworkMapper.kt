package com.example.composerecipeapp.helper.mapper

import com.example.composerecipeapp.model.RecipeModel
import com.example.composerecipeapp.model.RecipeNetworkModel

class RecipeNetworkMapper : EntityMapper<RecipeNetworkModel, RecipeModel> {

    override fun mapFromEntity(entity: RecipeNetworkModel) =
        RecipeModel(
            id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients ?: listOf(),
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated
        )

    override fun mapToEntity(domainModel: RecipeModel) = RecipeNetworkModel(
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

    fun fromEntityList(initial: List<RecipeNetworkModel>): List<RecipeModel> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<RecipeModel>): List<RecipeNetworkModel> {
        return initial.map { mapToEntity(it) }
    }
}