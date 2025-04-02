    package co.edu.eci.ecoappetite.server.domain.model;

    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.NoArgsConstructor;

    import org.springframework.data.mongodb.core.mapping.Document;

    @Document
    @Table(name = "roles")
    @NoArgsConstructor
    public class Rol {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Getter
        @Setter
        private Long id;

        @Enumerated(EnumType.STRING)
        @Getter
        @Setter
        private Role nombre;

        @JsonCreator
        public Rol(@JsonProperty("nombre") Role nombre) {
            this.nombre = nombre;
        }


        // Getters y setters
    }