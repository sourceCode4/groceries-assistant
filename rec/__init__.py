import implicit as implicit
import pandas as pandas
import scipy as scipy
from scipy.sparse import coo_matrix
import psycopg2 as psycopg2

def insert():
    #Establishing the connection
    conn = psycopg2.connect(
        database="TEST4", user='postgres', password='ana', host='127.0.0.1', port= '5432'
    )
    #Setting auto commit false
    conn.autocommit = True

    #Creating a cursor object using the cursor() method
    cursor = conn.cursor()

    # Preparing SQL queries to INSERT a record into the database.
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('0', '0', 0, 0, 5)''')
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('0', '1', 0, 0, 4)''')
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('0', '2', 0, 0, 0)''')
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('1', '0', 0, 0, 0)''')
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('1', '1', 0, 0, 4)''')
    cursor.execute('''INSERT INTO shopping(userid, foodid, COUNT, PREF,
       AMOUNT) VALUES ('1', '2', 0, 0, 0)''')

    # Commit your changes in the database
    conn.commit()
    print("Records inserted........")

    # Closing the connection
    conn.close()

def fetch():
    conn = psycopg2.connect(
        database="TEST4", user='postgres', password='ana', host='127.0.0.1', port= '5432'
    )

    #Setting auto commit false
    conn.autocommit = True

    #Creating a cursor object using the cursor() method
    cursor = conn.cursor()

    #Retrieving data
    cursor.execute('''SELECT userid, foodid, amount from shopping''')

    #Fetching 1st row from the table
    result = cursor.fetchone();
    print(result)

    #Fetching 1st row from the table
    result = cursor.fetchall();
    print(result)

    #Here there should be code to make a numpy array such that it looks like this
    # [ [1, 2, 3] , [1, 2, 3] , [1, 2, 3] ]
    #where 1 is userid, 2 is foodid, 3 is amount

    #Commit your changes in the database
    conn.commit()

    #Closing the connection
    conn.close()

def analyse(arr):
    data = pandas.DataFrame(arr, columns = ['userid','foodid','amount'])
    newdata = coo_matrix((data['c'].astype(float),
                        (data['a'].astype(int),
                         data['b'].astype(int))))

    newdata = newdata.tocsr()
    print(newdata)
    model = implicit.als.AlternatingLeastSquares(factors=50)
    model.fit(newdata)

    recommendations = model.recommend(3, newdata[3], N=3, recalculate_user=True)
    print(recommendations)

if __name__ == '__main__':
    insert()
    fetch()