package sx.lit.coursecatalogeservicev2.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sx.lit.course.coursecatalogeservicev2.api.Instructor
import sx.lit.course.coursecatalogeservicev2.models.*
import sx.lit.coursecatalogeservicev2.exception.InstructorNotValidException
import sx.lit.coursecatalogeservicev2.model.InstructorEntity
import sx.lit.coursecatalogeservicev2.service.InstructorService

@RestController
class InstructorController(private val instructorService: InstructorService) : Instructor {
    override fun addInstructor(createInstructorRequest: CreateInstructorRequest): ResponseEntity<InstructorDto> {
        val instructorDto = instructorService.addInstructor(InstructorEntity(name = createInstructorRequest.name!!))
        return ResponseEntity(instructorDto, HttpStatus.CREATED)
    }

    override fun deleteInstructorById(instructorId: Int): ResponseEntity<Unit> {
        return ResponseEntity(instructorService.deleteById(instructorId), HttpStatus.NO_CONTENT)
    }

    override fun getAllInstructors(): ResponseEntity<List<InstructorDto>> {
        return ResponseEntity(instructorService.findAll(), HttpStatus.OK)
    }

    override fun getInstructorById(instructorId: Int): ResponseEntity<InstructorDto> {
        val instructor = instructorService.findByInstructorId(instructorId)
            .map { instructor -> InstructorDto(instructor.id, instructor.name, instructor.courses.map { CourseDto(it.id, it.name, it.category, it.instructorEntity?.id) }) }

        if (!instructor.isPresent) {
            throw InstructorNotValidException("No Instructor found with ID: $instructorId")
        }

        return ResponseEntity(instructor.get(), HttpStatus.OK)
    }

    override fun updateInstructorById(instructorId: Int, updateInstructorRequest: UpdateInstructorRequest): ResponseEntity<Unit> {
        if (!instructorService.findByInstructorId(instructorId).isPresent) {
            throw InstructorNotValidException("No Instructor found with ID: $instructorId")
        }

        return ResponseEntity(instructorService.updateInstructor(InstructorDto( id = instructorId, name = updateInstructorRequest.name)), HttpStatus.NO_CONTENT)
    }
}
