Fruit : void grow();void harvest();void plant();
产品
	*Apple: int treeAge;
	*Grap: boolean seedless;
	*Strawberry
工厂
    FruitGardener

简单工厂模式的缺点：
     1） 把产品的所有创建逻辑都在工厂类中，不易于扩展