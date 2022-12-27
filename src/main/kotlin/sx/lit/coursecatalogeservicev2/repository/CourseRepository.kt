package sx.lit.coursecatalogeservicev2.repository

import org.springframework.data.repository.CrudRepository
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import java.util.*

interface CourseRepository : CrudRepository<CourseEntity, UUID>
