package bookstore.back.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Integer id) {
        super(createMessage(entity, id));
    }

    private static String createMessage(String entity, Integer id) {
        if (id == null) {
            throw new BusinessException("'ID' de " + entity + " não pode ser nulo.");
        } else {
            return "Não foi possível encontrar " + entity + " com o id: " + id;
        }
    }

}
