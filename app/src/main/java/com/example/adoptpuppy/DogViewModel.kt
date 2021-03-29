package com.example.adoptpuppy

import com.airbnb.mvrx.*

interface StableItem {
    val stableId: Any
}


data class Dog(
        val id: Long,
        val name: String,
        val breeds: String,
        val imageUrl: String,
        val description: String,
        var expanded: Boolean = false
) : StableItem {
    override val stableId = id
}

data class DogState(
        val dogs: Async<List<Dog>> = Uninitialized,
        val lovedDogId: Long? = null,
        val adoptionRequest: Async<Dog> = Uninitialized,
) : MavericksState {
    val lovedDog: Dog? = dog(lovedDogId)

    private fun dog(dogId: Long?): Dog? = dogs()?.firstOrNull { it.id == dogId }
}


class DogViewModel(
        initialState: DogState,
        private val dogRepository: DogRepository
) : MavericksViewModel<DogState>(initialState) {

    init {
        suspend {
            dogRepository.getDogs()
        }.execute {
            copy(dogs = it)
        }
    }

    fun loveDog(dogId: Long) = setState { copy(lovedDogId = dogId) }

    fun adoptLovedDog() = withState { state ->
        val lovedDog = state.lovedDog ?: throw IllegalStateException("You must love a dog first!")
        suspend {
            dogRepository.adoptDog(lovedDog)
        }.execute { copy(adoptionRequest = it) }
    }

    companion object : MavericksViewModelFactory<DogViewModel, DogState> {
        override fun create(viewModelContext: ViewModelContext, state: DogState): DogViewModel {
            val dogRepository = viewModelContext.app<DogApplication>().dogsRepository
            return DogViewModel(state, dogRepository)
        }
    }
}