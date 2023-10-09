package bookstore.back.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity,Long id){
        super("Não foi possível encontrar "+ entity +" com o id: "+ id);
    }
}
