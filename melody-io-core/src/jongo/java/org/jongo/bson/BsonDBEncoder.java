/*
 * Copyright (C) 2011 Benoît GUÉROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jongo.bson;

import java.io.IOException;

import org.bson.BSONObject;
import org.bson.io.OutputBuffer;

import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import com.mongodb.DefaultDBEncoder;
import com.mongodb.LazyDBObject;
import com.mongodb.MongoException;

public class BsonDBEncoder implements DBEncoder {

	public final static DBEncoderFactory FACTORY = new BsonDBEncoderFactory();

	private BsonDBEncoder() {
	}

	public int writeObject(final OutputBuffer buf, BSONObject o) {

		if (!(o instanceof LazyDBObject)) {
			return DefaultDBEncoder.FACTORY.create().writeObject(buf, o);
		}

		try {
			return ((LazyDBObject) o).pipe(buf);
		} catch (IOException e) {
			throw new MongoException("Exception serializing a LazyDBObject", e);
		}
	}

	private static class BsonDBEncoderFactory implements DBEncoderFactory {
		public DBEncoder create() {
			return new BsonDBEncoder();
		}

	}
}
