package ru.yandex.practicum.filmorate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.validation.Validation;
import javax.validation.Validator;
import ru.yandex.practicum.filmorate.model.Users;


@SpringBootTest
class FilmorateApplicationTests {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	public void testEmptyFilm() {
		Film film = Film.builder().build();
		assertEquals(3, validator.validate(film).size()); // проверяем, что есть две ошибки валидации
	}

	@Test
	public void testInvalidFilm() {
		Film film = Film.builder()
				.id(1)
				.name("")
				.description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus quis duiвыапвыаыпвыапвыа" +
						"авапвывапролрпавывапролропаваыаыпавпвпвпававапвпвпвпвапвапваsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"пвапвавываыЭdsfssssssssdassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
						"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
				.releaseDate(LocalDate.of(1800, 1, 1))
				.duration(-10)
				.build();
		assertEquals(3, validator.validate(film).size()); // проверяем, что есть четыре ошибки валидации
	}
}
