package idgeneration;

import storages.IdentifiersRepository;

public class IdGeneratorImpl implements IdGenerator {
    @Override
    public long next() {
        //TODO: stub, logic is same as in UniqueIdService
        return counter;
    }

    private IdentifiersRepository identifiersRepository;
    private long counter;
}
