package sx.lit.coursecatalogeservicev2.repository

import org.springframework.data.repository.CrudRepository
import sx.lit.coursecatalogeservicev2.model.InstructorEntity

interface InstructorRepository : CrudRepository<InstructorEntity, Int>
