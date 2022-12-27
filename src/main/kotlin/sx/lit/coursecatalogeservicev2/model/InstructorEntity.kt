package sx.lit.coursecatalogeservicev2.model

import javax.persistence.*

@Entity
@Table(name="INSTRUCTORS")
data class InstructorEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Int? = null,
    var name: String,
    @OneToMany(
        mappedBy = "instructorEntity",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var courses : List<CourseEntity> = mutableListOf()
)
