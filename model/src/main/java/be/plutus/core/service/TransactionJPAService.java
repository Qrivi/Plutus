package be.plutus.core.service;

import be.plutus.common.DateService;
import be.plutus.core.exception.DuplicateTransactionException;
import be.plutus.core.exception.InvalidTransactionIdentifierException;
import be.plutus.core.model.Card;
import be.plutus.core.model.Location;
import be.plutus.core.model.Product;
import be.plutus.core.model.Transaction;
import be.plutus.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class TransactionJPAService implements TransactionService{

    private final TransactionRepository repository;

    @Autowired
    public TransactionJPAService( TransactionRepository repository ){
        this.repository = repository;
    }

    @Override
    public List<Transaction> getAllTransactions(){
        return repository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByCard( Card card ){
        return repository.findByCard( card );
    }

    @Override
    public List<Transaction> getTransactionsByLocation( Location location ){
        return repository.findByLocation( location );
    }

    @Override
    public List<Transaction> getTransactionsByTimestamp( ZonedDateTime timestamp ){
        return repository.findByTimestamp( timestamp );
    }

    @Override
    public List<Transaction> getTransactionsByCardAndLocation( Card card, Location location ){
        return repository.findByCardAndLocation( card, location );
    }

    @Override
    public List<Transaction> getTransactionsByCardAndTimestamp( Card card, ZonedDateTime after, ZonedDateTime before ){
        if( after == null ) after = DateService.now().minusYears( 5 );
        if( before == null ) before = DateService.now();

        return repository.findByCardAndTimestampBetween( card, after, before );
    }

    @Override
    public Transaction getTransactionById( Integer id ){
        if( id == null )
            throw new InvalidTransactionIdentifierException();
        return repository.findOne( id );
    }

    @Override
    public Transaction getTransactionByCardAndTimestamp( Card card, ZonedDateTime timestamp ){
        return repository.findByCardAndTimestamp( card, timestamp );
    }

    @Override
    public Transaction createTransaction( ZonedDateTime timestamp, Card card, Location location, List<Product> products ){
        Transaction transaction = new Transaction();

        if( this.getTransactionByCardAndTimestamp( card, timestamp ) != null )
            throw new DuplicateTransactionException( timestamp );

        transaction.setTimestamp( timestamp );
        transaction.setCard( card );
        transaction.setLocation( location );
        transaction.setProducts( products != null ? products : new ArrayList<>() );

        return repository.save( transaction );
    }

    @Override
    public void updateTransactionProducts( int id, List<Product> products ){
        Transaction transaction = this.getTransactionById( id );

        transaction.setProducts( products );

        repository.save( transaction );
    }

    @Override
    public void addTransactionProducts( int id, List<Product> products ){
        Transaction transaction = this.getTransactionById( id );

        transaction.setProducts(
                Stream.concat( transaction.getProducts().stream(), products.stream() )
                        .collect( Collectors.toList() )
        );

        repository.save( transaction );
    }

    @Override
    public void addTransactionProduct( int id, Product product ){
        Transaction transaction = this.getTransactionById( id );

        transaction.addProduct( product );

        repository.save( transaction );
    }

    @Override
    public void removeTransaction( int id ){
        Transaction transaction = this.getTransactionById( id );

        repository.delete( transaction );
    }
}
