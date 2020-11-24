package com.jconf.ca.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class EntitiesTests {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    val latitude = "14.622784025555436";
    val longitude = "-90.55425550456252";

    @Test
    fun `When a category is created, Then categoryRepository-findById should return the new category `() {
        var firstCategory = Category(name = "Category for test")
        var newId = entityManager.persistAndGetId(firstCategory) as Long

        var findCategoryResult = categoryRepository.findById(newId)
        assertThat(findCategoryResult.get().name).isEqualTo(firstCategory.name)
    }

    @Test
    fun `When an event is created, Then eventRepository-findById should return the new event`() {
        var partyCategory = Category(name = "Party!")
        entityManager.persistAndGetId(partyCategory) as Long

        var partyAtCampus = Event(name = "Party",
                address = "Campus",
                description = "Party at campus!",
                category = partyCategory,
                latitude = latitude,
                longitude = longitude)

        eventRepository.save(partyAtCampus)

        assertThat(eventRepository.findById(partyAtCampus.id!!))
                .isPresent
    }

    @Test
    fun `When an category has many events, Then eventRepository-findByCategoryId should return the same number of events`() {
        var maintenanceCat = Category(name = "Server maintenance")
        entityManager.persistAndGetId(maintenanceCat) as Long

        var servicesToMaintenance = listOf(
                Event(name = "Database upgrade",
                        address = "Server room",
                        description = "Database upgrade",
                        category = maintenanceCat,
                        latitude = latitude,
                        longitude = longitude),
                Event(name = "Mail upgrade",
                        address = "Server room",
                        description = "Mail upgrade",
                        category = maintenanceCat,
                        latitude = latitude,
                        longitude = longitude),
                Event(name = "Firewall update",
                        address = "Server room",
                        description = "Firewalls update",
                        category = maintenanceCat,
                        latitude = latitude,
                        longitude = longitude)
        )
        eventRepository.saveAll(servicesToMaintenance)

        var allEvents = eventRepository.findByCategoryId(maintenanceCat.id!!)
        assertThat(allEvents.count()).isEqualTo(servicesToMaintenance.count())
    }

}