# Let's formulate a problem for ourselves and solve it with the help of monads of states.
# Problem description:
# A miner can find different finds in the mine, each of which can be extracted with a certain number of tools,
# it is necessary to write a solution that would allow you to get a list of the extracted loot in the order of
# its receipt, as well as the strength of all the tools available to the miner. An important point is when a miner
# extracts any material, a unit of strength of the corresponding tool is spent.

from pymonad.tools import curry
from pymonad.state import State

miner_stats = {
    'backpack': [],
    'tools': {
        'shovel_durability': 2,
        'pickaxe_durability': 1}
}

potential_loot = {
    '20$ bill': 'shovel',
    'beautiful beetle': 'shovel',
    'coal': 'pickaxe',
    'dino_skeleton': 'pickaxe',
    'diamond': 'pickaxe',
}

miner_state = State.insert(miner_stats['backpack'])


@curry(2)
def dig(item_key, miner_backpack):
    def count_computation(miner_tools):
        tool_type = potential_loot[item_key]
        miner_tools[f'{tool_type}_durability'] -= 1
        return miner_backpack + [item_key], miner_tools
    return State(count_computation)


finale = (
    miner_state
    .then(dig('beautiful beetle'))
    .then(dig('beautiful beetle'))
    .then(dig('beautiful beetle'))
    .then(dig('coal'))
)

m = finale.run(miner_stats['tools'])
print(m)
