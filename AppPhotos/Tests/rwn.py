import rowordnet


wn = rowordnet.RoWordNet()

word = 'arbore'
synset_id = wn.synsets(literal=word)

wn.print_synset(synset_id)

synset_object = wn.synset(synset_id)
synset_object = wn(synset_id) # equivalent, shorter form

print("Print its literals (synonym words): {}".format(synset_object.literals))
print("Print its definition: {}".format(synset_object.definition))
print("Print its ID: {}".format(synset_object.id))