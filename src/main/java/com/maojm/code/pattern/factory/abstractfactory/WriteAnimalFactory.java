package com.maojm.code.pattern.factory.abstractfactory;

public class WriteAnimalFactory implements IAnimalFactory {

	@Override
	public ICat createCat() {
		return new WriteCat();
	}

	@Override
	public IDog createDog() {
		return new WriteDog();
	}

}
