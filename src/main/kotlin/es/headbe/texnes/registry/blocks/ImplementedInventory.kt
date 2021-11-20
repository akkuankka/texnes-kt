package es.headbe.texnes.registry.blocks

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

/**
 * A simple {@code Inventory} impl with default methods and an item getter
 */

interface ImplementedInventory : Inventory {
    var items: DefaultedList<ItemStack>

    companion object {
        fun of(items: DefaultedList<ItemStack>): ImplementedInventory?  {
            return object : ImplementedInventory {
                override var items = items
            }
        }

        fun ofSize(size: Int): ImplementedInventory? =
            of(DefaultedList.ofSize(size, ItemStack.EMPTY))

    }

    override fun size(): Int = items.size
    override fun isEmpty(): Boolean {
        for (i in 0 until size()) {
            val stack = getStack(i)
            if (!stack.isEmpty) {
                return false
            }
        }
        return true
    }

    override fun getStack(slot: Int): ItemStack = items[slot]
    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack {
        return Inventories.removeStack(items, slot)
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
    }

    override fun clear() {
        items.clear()
    }

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity): Boolean = true
}