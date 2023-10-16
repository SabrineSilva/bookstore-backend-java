package bookstore.back.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Integer id) {
        super(createMessage(entity, id));
    }

    private static String createMessage(String entity, Integer id) {
        return (id == null) ? "'ID' não pode ser nulo." : "Não foi possível encontrar " + entity + " com o id: " + id;
    }
}
