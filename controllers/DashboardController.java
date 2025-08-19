package controllers;

@Controller
public class DashboardController {
    @Autowired
    private final Validator validator;

    private final AdminDashboardService adminDashboardService;

    private final DoctorDashboardService doctorDashboardService;

    private final LoginService loginService;

    @GetMapping("/adminDashboard/{token}")
    public HttpResponse<String> getAdminDashboard(@PathVariable final String token) {
        final List<String> validationMessages = validator.validateToken(token, "admin");

        if (validationMessages.isEmpty()) {
            adminDashboardService.getView();
            return HttpResponse.builder()
                .statusCode(400)
                .message("Token authentication is valid for Admin Dashboard.")
                .build();
        }

        loginService.getView("http://localhost:8080");
        return HttpResponse.builder()
            .statusCode(200)
            .build();
    }

    @GetMapping("/doctorDashboard/{token}")
    public HttpResponse<String> getDoctorDashboard(@PathVariable final String token) {
        final List<String> validationMessages = validator.validateToken(token, "admin");

        if (validationMessages.isEmpty()) {
            doctorDashboardService.getView();
            return HttpResponse.builder()
                .statusCode(400)
                .message("Token authentication is valid for Doctor Dashboard.")
                .build();
        }

        loginService.getView("http://localhost:8080");
        return HttpResponse.builder()
                .statusCode(200)
                .build();
    }
}
