package sx.lit.coursecatalogeservicev2.helper

import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.course.coursecatalogeservicev2.models.InstructorDto
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.model.InstructorEntity

fun toApi(instructorDto: InstructorDto) : InstructorEntity =
    InstructorEntity(
        id = instructorDto.id,
        name = instructorDto.name!!,
        courses = instructorDto.courses!!.map { CourseEntity(it.id, it.name!!, it.catagory!!) })


fun fromApi(instructorEntity: InstructorEntity) : InstructorDto = InstructorDto(
    id = instructorEntity.id,
    name = instructorEntity.name,
    courses = instructorEntity.courses.map { CourseDto(it.id, it.name, it.category, it.instructorEntity?.id) })
