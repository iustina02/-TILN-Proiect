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

synset_id = wn.synsets("tren")[2] # select the third synset from all synsets containing word "tren"
print("\nPrint all outbound relations of {}".format(wn.synset(synset_id)))
outbound_relations = wn.outbound_relations(synset_id)
for outbound_relation in outbound_relations:
    target_synset_id = outbound_relation[0]        
    relation = outbound_relation[1]
    print("\tRelation [{}] to synset {}".format(relation,wn.synset(target_synset_id)))