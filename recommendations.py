#!/usr/bin/env python
# coding: utf-8

# In[23]:


import implicit as implicit
import pandas as pandas
from scipy.sparse import coo_matrix
import psycopg2 as psy
import numpy as np


def runner(arr, userid):
    data = pandas.DataFrame(arr, columns=['userid', 'foodid', 'amount'])
    newdata = coo_matrix((data['amount'].astype(float),
                          (data['userid'].astype(int),
                           data['foodid'].astype(int))))

    newdata = newdata.tocsr()
    model = implicit.als.AlternatingLeastSquares(factors=50)
    model.fit(newdata)

    recommendations = model.recommend(userid, newdata[userid], N=376, recalculate_user=True)
#     print(recommendations)
    return recommendations


def updatePrefs(rec, userid):
    conn = psy.connect(database="groceriesassistant", user="postgres", password="ana", host="127.0.0.1", port="5432")
#     print("Opened database successfully")

    cur = conn.cursor()
    
    item = rec[0]
    rating = rec[1]

    for i in range(0, len(item)):
        a = "UPDATE shopping7 SET PREF = " + str(rating[i]) +" WHERE userid = "+ str(userid) + " AND foodid = "+str(item[i])
#         print(a)
        cur.execute(a)
        conn.commit()

    conn.close()

def insert():
    #Establishing the connection
    conn = psy.connect(
        database="groceriesassistant", user='postgres', password='ana', host='127.0.0.1', port= '5432'
    )
    #Setting auto commit false
    conn.autocommit = True

    #Creating a cursor object using the cursor() method
    cursor = conn.cursor()

    # Preparing SQL queries to INSERT a record into the database.
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (0, 0, 0, 0, 5)''')
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (0, 1, 0, 0, 4)''')
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (0, 2, 0, 0, 0)''')
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (1, 0, 0, 0, 0)''')
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (1, 1, 0, 0, 4)''')
    cursor.execute('''INSERT INTO shopping7(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES (1, 2, 0, 0, 0)''')

    # Commit your changes in the database
    conn.commit()
    print("Records inserted........")

    # Closing the connection
    conn.close()

def fetch():
    conn = psy.connect(
        database="groceriesassistant", user='postgres', password='ana', host='127.0.0.1', port= '5432'
    )

    #Setting auto commit false
    conn.autocommit = True

    #Creating a cursor object using the cursor() method
    cursor = conn.cursor()

    #Retrieving data
    cursor.execute('''SELECT userid, foodid, AMOUNT from shopping7''')

    #Fetching 1st row from the table
#     result = cursor.fetchone();
#     print(result)

    #Fetching 1st row from the table
    result = cursor.fetchall();
    print(result)

    a2D = np.array(result)
#     print(a2D)

    #Here there should be code to make a numpy array such that it looks like this
    # [ [1, 2, 3] , [1, 2, 3] , [1, 2, 3] ]
    #where 1 is userid, 2 is foodid, 3 is amount

    #Commit your changes in the database
    conn.commit()

    #Closing the connection
    conn.close()

    return a2D

def fetchafter():
    conn = psy.connect(
        database="groceriesassistant", user='postgres', password='ana', host='127.0.0.1', port= '5432'
    )

    #Setting auto commit false
    conn.autocommit = True

    #Creating a cursor object using the cursor() method
    cursor = conn.cursor()

    #Retrieving data
    cursor.execute('''SELECT userid, foodid, AMOUNT, PREF from shopping7''')

    #Fetching 1st row from the table
#     result = cursor.fetchone();
#     print(result)

    #Fetching 1st row from the table
    result = cursor.fetchall();
    print(result)

    a2D = np.array(result)
#     print(a2D)

    #Here there should be code to make a numpy array such that it looks like this
    # [ [1, 2, 3] , [1, 2, 3] , [1, 2, 3] ]
    #where 1 is userid, 2 is foodid, 3 is amount

    #Commit your changes in the database
    conn.commit()

    #Closing the connection
    conn.close()

    return a2D

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print(len(fetch()))
    for i in range(0, 2):
        updatePrefs(runner(fetch(), i), i)
#         print('done')
    fetchafter()


# In[ ]:




