//// GetUseCase Unit Tests (using Mockk)
//import com.example.icecaremobile.domain.model.Request.LoginRequest
//import com.example.icecaremobile.domain.model.network.ApiError
//import com.example.icecaremobile.domain.repository.IRepository
//import com.example.icecaremobile.domain.useCase.GetUseCase
//import com.example.icecaremobile.domain.auth.AuthManager
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//import io.mockk.coVerify
//import io.mockk.every
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.test.setMain
//import kotlinx.coroutines.test.resetMain
//
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class GetUseCaseTest {
//
//    private lateinit var repository: IRepository
//    private lateinit var authManager: AuthManager
//    private lateinit var getUseCase: GetUseCase
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//
//    @Before
//    fun setUp(){
//        Dispatchers.setMain(testDispatcher)
//        repository = mockk()
//        authManager = mockk(relaxed = true)
//        getUseCase = GetUseCase(mockk {
//            every { provideRepository() } returns repository
//        }, authManager)
//    }
//
//    @Test
//    fun testLogin_success() = runTest{
//        //Arrange
//        val loginRequest = LoginRequest("test@example.com", "password")
//        val expectedLoginResponse = LoginResponse(
//            message = "Login Successful",
//            statusCode = 200,
//            status = true,
//            data = LoginResponseData(
//                id = 6,
//                phone = "08034678912",
//                token = "rfgdssgdygsfguy463634",
//                status = "Approved",
//                accountNumber = "012567823",
//                email = "johndoe@gmail.com",
//                fullName = "John Doe",
//                userAccount = UserAccount(
//                    dollarRate = 1500.0,
//                    nairaBalance = "567000",
//                    dollarBalance = "50",
//                    companyNumber = listOf(
//                        CompanyPhones("0905678934"),
//                        CompanyPhones("0803678900"),
//                        CompanyPhones("0807678934")
//                    ),
//                    companyAccounts = listOf(
//                        CompanyAccounts(
//                            accountNumber = "2134789034",
//                            accountName = "Ice Care Nig Ltd",
//                            bankName = "UBA"
//                        ),
//                        CompanyAccounts(
//                            accountNumber = "0252458264",
//                            accountName = "Ice Care Nig Ltd",
//                            bankName = "Wema Bank"
//                        ),
//                        CompanyAccounts(
//                            accountNumber = "5678902314",
//                            accountName = "Ice Care Nig Ltd",
//                            bankName = "Providus Bank"
//                        )
//                    )
//                )
//            )
//        )
//        coEvery { repository.login(loginRequest, any(), any()) } answers {
//            thirdArg<(LoginResponse) -> Unit>().invoke(expectedLoginResponse)
//        }
//        var actualResponse : LoginResponse? = null
//
//        //Act
//        getUseCase(
//            loginRequest = loginRequest,
//            onSuccess = { response ->
//                actualResponse = response
//            },
//            onError = {}
//        )
//
//        //Assert
//        coVerify{authManager.saveLoginResponse(expectedLoginResponse)}
//        coVerify{authManager.saveUserAccountResponse(expectedLoginResponse.data.userAccount)}
//        assertEquals(expectedLoginResponse, actualResponse)
//
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun testLogin_failure() = runTest {
//        //Arrange
//        val loginRequest = LoginRequest("test@example.com", "password")
//        val expectedApiError = ApiError("Login failed", 400, "Error")
//        coEvery { repository.login(loginRequest, any(), any()) } answers {
//            thirdArg<(ApiError) -> Unit>().invoke(expectedApiError)
//        }
//
//        var actualError : ApiError? = null
//        //Act
//        getUseCase(
//            loginRequest = loginRequest,
//            onSuccess = {},
//            onError = { error ->
//                actualError = error
//            }
//        )
//
//        //Assert
//        assertEquals(expectedApiError, actualError)
//
//        Dispatchers.resetMain()
//    }
//}