package sx.lit.coursecatalogeservicev2.controller

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.junit.jupiter.Testcontainers
import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.course.coursecatalogeservicev2.models.UpdateCourseRequest
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.model.InstructorEntity
import sx.lit.coursecatalogeservicev2.repository.CourseRepository
import sx.lit.coursecatalogeservicev2.repository.InstructorRepository
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
internal class CourseControllerTest : PostgreSQLContainerInitializer() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setup() {
        instructorRepository.deleteAll();
        courseRepository.deleteAll()
        val instructor = instructorRepository.save(getDefaultInstructor())
        courseRepository.saveAll(courseEntityList(instructor))
    }

    private fun getDefaultInstructor(): InstructorEntity = InstructorEntity(id = 1, name = "Tony")

    private fun courseEntityList(instructor: InstructorEntity): List<CourseEntity> {
        return listOf(CourseEntity(UUID.randomUUID(), "default course", "kotlin", instructor))
    }

    @Test
    fun addCourse() {
        val instructor = instructorRepository.findAll().first()
        val courseDTO = CourseDto(null, "Kotling 101", "Kotling", instructor.id)

        val result = webTestClient.post()
            .uri("api/v1/course")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertTrue { result!!.id != null }
        assertEquals( courseDTO.name , result?.name)
        assertEquals( courseDTO.catagory , result?.catagory)
        assertEquals( courseDTO.instructorId , result?.instructorId)
    }

    @Test
    fun deleteCourseById() {
    }

    @Test
    fun getAllCourses() {
    }

    @Test
    fun getCourseById() {
    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findAll().first();
        val courseEntity = CourseEntity(null,
            "Apache Kafka for Developers using Spring Boot", "Development",
            instructor)
        courseRepository.save(courseEntity)
        val updatedCourseEntity = courseEntity.copy(name = "Kotlin for Beginners")

        val result = webTestClient.put()
            .uri("api/v1/course/${courseEntity.id}")
            .bodyValue(UpdateCourseRequest(name = updatedCourseEntity.name, category = courseEntity.category, instructorId = instructor.id!!))
            .exchange()
            .expectStatus().isNoContent

        courseRepository.findById(courseEntity.id!!)
            .let { assertTrue { it.get().name == "Kotlin for Beginners" } }
    }
}
