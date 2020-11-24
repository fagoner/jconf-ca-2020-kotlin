package com.jconf.ca.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.persistence.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Entity
@Table(name = "category")
class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,
        var name: String,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        var active: Boolean = true,
)

@Entity
@Table(name = "event")
class Event(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        var name: String,
        var address: String,
        var description: String,

        var latitude: String,
        var longitude: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: Category
)

interface CategoryRepository : CrudRepository<Category, Long>

interface EventRepository : CrudRepository<Event, Long> {
    fun findByCategoryId(id: Long): Iterable<Event>
}

@RestController
@RequestMapping("/api/categories")
class CategoryController {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @GetMapping
    fun findAll(): Iterable<Category> {
        return categoryRepository.findAll()
    }

}

@RestController
@RequestMapping("/api/events")
class EventController {

    @Autowired
    lateinit var eventRepository: EventRepository

    @GetMapping
    fun findAll(): Iterable<Event> {
        return eventRepository.findAll()
    }

}

