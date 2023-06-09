package pack.logindemo.app.model

data class UserDTO(
    val userId : Int = -1,
    val firstName : String,
    val lastName : String,
    val mobileNumber : String,
    val mPin : String,
    val confirmMpin : String,
)

data class CredsDTO (
    val firstName : String,
    val lastName : String,
    val mobileNumber : String,
    val mPin : String,
    val confirmMpin : String,
)