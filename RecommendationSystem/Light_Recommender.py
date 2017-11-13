import numpy
import tensorflow as tf
import pandas as pd
import os


os.environ['TF_CPP_MIN_LOG_LEVEL']='2'

# Lendo o dataset
df = pd.read_csv('ml-100k/u.data', sep='\t', names=['user', 'item', 'rate', 'time'])
# Extrai uma amostra aleatoria de 70% da original para usar como teste
msk = numpy.random.rand(len(df)) < 0.7
df_train = df[msk]
# Indexando usuários
user_index = [x - 1 for x in df_train.user.values]
item_index = [x - 1 for x in df_train.item.values]
rates = df_train.rate.values


# Cria uma matriz 943x1682 representando os UsuáriosXFilmes e a avaliação
feature_len = 10
U = tf.Variable(initial_value=tf.truncated_normal([943,feature_len]), name='users')
P = tf.Variable(initial_value=tf.truncated_normal([feature_len,1682]), name='items')
result = tf.matmul(U, P)
# "Amassa" a matriz e transforma ela em um vetor para economizar espaço e acesso
result_flatten = tf.reshape(result, [-1])

# Calcula a avaliação
R = tf.gather(result_flatten, user_index * tf.shape(result)[1] + item_index, name='extracting_user_rate')

# Funções de custos para calcular o 'trabalho' e o quão próximo da média real nossa estimativa se encontra
diff_op = tf.subtract(R, rates, name='training_diff')
diff_op_squared = tf.abs(diff_op, name="squared_difference")
base_cost = tf.reduce_sum(diff_op_squared, name="sum_squared_error")

lda = tf.constant(.001, name='lambda')
norm_sums = tf.add(tf.reduce_sum(tf.abs(U, name='user_abs'), name='user_norm'),
                   tf.reduce_sum(tf.abs(P, name='item_abs'), name='item_norm'))
regularizer = tf.multiply(norm_sums, lda, 'regularizer')


lr = tf.constant(.001, name='learning_rate')
global_step = tf.Variable(0, trainable=False)
learning_rate = tf.train.exponential_decay(lr, global_step, 10000, 0.96, staircase=True)
optimizer = tf.train.GradientDescentOptimizer(learning_rate)
training_step = optimizer.minimize(base_cost, global_step=global_step)

# Execução do treinamento
sess = tf.Session()
init = tf.global_variables_initializer()
sess.run(init)

for i in range(1000):
    sess.run(training_step)

#Menu e execução
op = 1
dt = []
while op != 0:

    Z = int(input('Usuario: '))

    for j in range(50):
        test = tf.gather(tf.gather(result, Z), j)
        dt.append(sess.run(test))
    ddf = pd.DataFrame({'ratings': dt})
    ddf['ratings'] = ddf['ratings'].astype('float')
# ddf.sort_values(by='ratings', ascending=False, inplace=True)
# print(ddf)

    movie_df = pd.read_csv('ml-100k/u.item', sep='|', names=['Id', 'Title', 'release',
                                                             'vDate', 'imdb', 'unknown', 'action', 'adventure',
                                                             'animation', 'children', 'comedy', 'crime',
                                                             'documentary', 'drama', 'fantasy', 'noir', 'horror',
                                                             'musical', 'mystery', 'romance', 'sci-fi',
                                                             'thriller', 'war', 'western'], encoding='latin-1')
    movie_df = movie_df[['Title']]
    final_df = pd.merge(ddf, movie_df, left_index=True, right_index=True)
    final_df.sort_values(by='ratings', ascending=False, inplace=True)
    print('TOP 10 RECOMENDAÇÕES:\n', final_df['Title'].head(10))

    gt = (input('Sair? (S/N): '))
    if gt == 'S' or 's':
        op = 0
    else:
        op = 1