package br.com.backendapi.controller;

import br.com.backendapi.business.IPessoaBusiness;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

@WebMvcTest
class PessoaControllerTest {

    @Autowired
    private PessoaController pessoaController;

    @MockBean
    private IPessoaBusiness iPessoaBusiness;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.pessoaController);
    }

    @Test
    public void retornarNotFound_quandoListar() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/pessoas" )
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void retornarSuccess_quandoListar() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/pessoa" )
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void retornarBadReques_quandoEditar() {
        given()
                .accept(ContentType.JSON)
                .when()
                .put("/pessoa/{id}", -2 )
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void retornarSuccess_quandoBuscarPorId() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/pessoa/{id}", 1 )
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void retornarSuccess_quandoExcluir() {
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/pessoa/{id}", 1 )
                .then()
                .statusCode(HttpStatus.OK.value());
    }


}