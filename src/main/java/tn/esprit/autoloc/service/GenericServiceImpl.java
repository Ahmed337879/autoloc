package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    // Retourne le nom de l'entité
    protected abstract String getEntityName();

    // Retourne la valeur de l'identifiant d'une entité
    protected abstract ID getId(T entity);

    // Copie les champs de source vers l'entité existante
    protected abstract void setFields(T source, T existing);

    @Override
    public boolean check(T entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        return isNew || getId(entity) != null;
    }

    @Override
    public T create(T entity) {
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant de l'entité " + getEntityName() + " ne doit pas être renseigné");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Les données de l'entité " + getEntityName() + " ne sont pas valides");
        }
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), String.valueOf(id)));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existing = findById(id);
        setFields(entity, existing);
        if (!check(existing, false)) {
            throw new IllegalArgumentException("Les données de l'entité " + getEntityName() + " ne sont pas valides");
        }
        return repository.save(existing);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), String.valueOf(id));
        }
        repository.deleteById(id);
    }
}
