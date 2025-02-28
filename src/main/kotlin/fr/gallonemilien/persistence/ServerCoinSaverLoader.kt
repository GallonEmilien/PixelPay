package fr.gallonemilien.persistence

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.coin.CoinType
import net.minecraft.command.argument.EntityArgumentType.getPlayers
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.MinecraftServer
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import java.util.*
import kotlin.collections.HashMap

class ServerCoinSaverLoader : PersistentState() {
    val playersData : HashMap<UUID, PlayerCoinData> = HashMap()
    companion object {
        fun getPlayerState(player : LivingEntity) : PlayerCoinData {
            val serverState : ServerCoinSaverLoader = getServerState(player.world.server!!)
            val playerState : PlayerCoinData = serverState.playersData.computeIfAbsent(player.uuid) { PlayerCoinData() }
            return playerState
        }

        fun createFromNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup?): ServerCoinSaverLoader {
            val serverCoinSaverLoader = ServerCoinSaverLoader()
            println(tag.toString())
            val playersNbt = tag.getCompound("players")
            println(playersNbt.keys.size)
            playersNbt.keys.forEach { key ->
                val playerCoinData = PlayerCoinData()
                val playerNbt = playersNbt.getCompound(key)
                CoinType.entries.forEach { coinType ->
                    val balance = playerNbt.getInt(coinType.name)
                    println("${coinType.name}: $balance")
                    playerCoinData.dataMap[coinType] = balance
                }
                val uuid = UUID.fromString(key)
                serverCoinSaverLoader.playersData[uuid] = playerCoinData
            }
            return serverCoinSaverLoader
        }

        fun createNew() : ServerCoinSaverLoader =
            ServerCoinSaverLoader()

        private val type: Type<ServerCoinSaverLoader> = Type(
            ServerCoinSaverLoader::createNew, // Si 'StateSaverAndLoader' n'existe pas encore, en crée un et met à jour les variables
            ServerCoinSaverLoader::createFromNbt, // Si un 'StateSaverAndLoader' NBT existe, le parse avec 'createFromNbt'
            null // Supposé être un enum 'DataFixTypes', mais on peut simplement passer null
        )

        fun getServerState(server: MinecraftServer): ServerCoinSaverLoader {
            val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager
            val state = persistentStateManager.getOrCreate(
                type,
                MOD_ID
            )
            state.markDirty()
            return state
        }
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?): NbtCompound {
        val playersNbt = NbtCompound()
        playersData.forEach { (uuid, playerCoinData) ->
            val playerNbt = NbtCompound()
            playerCoinData.dataMap.forEach { (coinType, balance) ->
                playerNbt.putInt(coinType.name, balance)
                println("${coinType.name}: $balance")
            }
            playersNbt.put(uuid.toString(), playerNbt)
        }
        val playersNbtFull = NbtCompound()
        playersNbtFull.put("players", playersNbt)
        return playersNbtFull
    }
}