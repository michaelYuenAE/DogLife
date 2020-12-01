package com.example.doglife.model

import java.util.*

data class Dog (
    val id: String,
    val ownerId: String,
    val iconUrl: String,
    val name: String?,
    val gender: Gender,
    val animalType: AnimalType = AnimalType.Dog,
    val age: DogAge,
    val registerDate: Date,
    val country: CountryCode,
    val address: String,
    val postalCode: String,
    val saved: Boolean,
    val color: DogColor?,
    val otherColor: String?,
    val breed: String?,
    val size: DogSize?,
    val coatLength: CoatLength?,
    val goodWith: GoodWith?,
    val careAndBehavior: CareAndBehavior?
)

enum class GoodWith {
    Kids,
    OtherDogs,
    Cat
}

enum class DogColor {
    Black,
    White,
    Brown,
    Red,
    Gold,
    Blue,
    Gray,
    Cream,
    Yellow,
    Other
}

enum class DogAge {
    Puppy, Young, Adult, Senior
}

enum class Gender {
    Male,
    Female
}

enum class DogSize {
    Small,
    Medium,
    Large,
    ExtraLarge
}

enum class CoatLength {
    Hairless,
    Short,
    Medium,
    Long,
    Wire,
    Curly
}

enum class AnimalType {
    Dog,
    Cat,
    Other
}

enum class CountryCode {
    HK
}

enum class CareAndBehavior {
    HouseTrained,
    SpecialNeed
}

