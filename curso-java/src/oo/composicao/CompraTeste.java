package oo.composicao;

public class CompraTeste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Compra c1 = new Compra();
		c1.cliente = "";
		c1.adicionarItem(new Item("Caneta", 20, 7.45));
		c1.adicionarItem(new Item("Caneta", 12, 3.89));
		c1.adicionarItem(new Item("Caderno", 3, 18.79));
		
		System.out.println(c1.itens.size());
		System.out.println(c1.obterValorTotal());
		
		double total = c1.itens.get(0).compra.itens.get(1).compra.obterValorTotal();
		System.out.println("O total eh" + total);
	}

}
