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
import sx.lit.course.coursecatalogeservicev2.models.InstructorDto
import sx.lit.course.coursecatalogeservicev2.models.UpdateInstructorRequest
import sx.lit.coursecatalogeservicev2.model.InstructorEntity
import sx.lit.coursecatalogeservicev2.repository.CourseRepository
import sx.lit.coursecatalogeservicev2.repository.InstructorRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
internal class InstructorControllerTest : PostgreSQLContainerInitializer() {

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
        instructorRepository.save(getDefaultInstructor())
    }

    private fun getDefaultInstructor(): InstructorEntity = InstructorEntity(id = 1, name = "Tony")

    @Test
    fun addInstructor() {
        val instructorDTO = InstructorDto(null, "Sherwin", listOf())

        val result = webTestClient.post()
            .uri("api/v1/instructor")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult()
            .responseBody

        assertTrue { result!!.id != null }
        assertEquals(instructorDTO.name, result?.name)
    }

    @Test
    fun deleteInstructorById() {
        val instructor = instructorRepository.findAll().first();

        webTestClient.delete()
            .uri("api/v1/instructor/{instructorId}", instructor.id)
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun getAllInstructors() {
        val result = webTestClient.get()
            .uri("api/v1/instructor")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(InstructorDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(1, result?.size)
        assertTrue { result!!.first().name == "Tony" }
    }

    @Test
    fun getInstructorById() {
        val instructorId = instructorRepository.save(InstructorEntity(name = "Bob")).id

        val result = webTestClient.get()
            .uri("api/v1/instructor/{instructorId}", instructorId)
            .exchange()
            .expectStatus().isOk
            .expectBody(InstructorDto::class.java)
            .returnResult()
            .responseBody

        assertTrue { result?.name == "Bob" }
    }

    @Test
    fun updateInstructorById() {
        val instructorId = instructorRepository.save(getDefaultInstructor()).id

        val request = UpdateInstructorRequest("Sherwin")

        webTestClient.put()
            .uri("api/v1/instructor/{instructorId}", instructorId!!)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNoContent

        instructorRepository.findById(1)
            .map { assertTrue { it.name == "Sherwin" } }
    }
}
