package hcmut.spss.be.config;

import hcmut.spss.be.entity.sharedLibrary.SharedLibrary;
import hcmut.spss.be.repository.SharedLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultLibraryInitializer implements CommandLineRunner {

    private final SharedLibraryRepository sharedLibraryRepository;

    @Override
    public void run(String... args) {

        if (sharedLibraryRepository.findByDefaultLibraryTrue().isEmpty()) {

            SharedLibrary defaultLibrary = SharedLibrary.builder()
                    .libraryId(1L)
                    .libraryName("Default Public Library")
                    .defaultLibrary(true)
                    .build();
            sharedLibraryRepository.save(defaultLibrary);
            System.out.println("Default library created with ID 1");
        } else {
            System.out.println("Default library already exists.");
        }
    }
}
