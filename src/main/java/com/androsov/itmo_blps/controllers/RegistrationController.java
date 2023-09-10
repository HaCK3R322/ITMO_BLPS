package com.androsov.itmo_blps.controllers;

//
//@RestController
//@CrossOrigin
//@AllArgsConstructor
//public class RegistrationController {
//    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());
//
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    @GetMapping("/register")
//    public String registrationForm() {
//        return "POST here username and password duh";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestParam String username,
//                                       @RequestParam String password,
//                                       @RequestParam String roleName) {
//
//        UserRegistrationRequest request = new UserRegistrationRequest(username,
//                password,
//                roleName);
//
//        User user = userService.registerFromRequest(request);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
//    }
//
//    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    public String login(@RequestParam String username,
//                        @RequestParam String password) throws Exception {
//        if(!userRepository.existsByUsername(username)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that username not found");
//        }
//
//        User user = userService.getByUsername(username);
//
//        String dbPassword = userRepository.getByUsername(user.getUsername()).getPassword();
//
//        // check by BCryptPasswordEncoder
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        if (encoder.matches(password, dbPassword)) {
//            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
//                    user.getPassword(),
//                    user.getRole().getPrivileges().stream()
//                            .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
//                            .collect(Collectors.toList()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            throw new Exception("Wrong password");
//        }
//
//        return "authenticated successfully!";
//    }
//}

