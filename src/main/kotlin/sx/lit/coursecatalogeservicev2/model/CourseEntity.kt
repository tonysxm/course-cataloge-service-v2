package sx.lit.coursecatalogeservicev2.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "COURSES")
data class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID?,
    var name: String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INSTRUCTOR_ID", nullable = false)
    val instructorEntity: InstructorEntity? = null
) {
    override fun toString(): String {
        return "Course(id=$id, name=$name, category=$category, instructor=${instructorEntity!!.id}"
    }
}
